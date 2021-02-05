package com.gsa.ui.login

import androidx.lifecycle.MutableLiveData
import com.dartmic.yo2see.base.AbstractViewModel
import com.dartmic.yo2see.base.SingleLiveEvent
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.SearchEvent
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson

import retrofit2.HttpException

class ProductListnViewModel(
    private val productListRepository: ProductListRepository,
    private val scheduler: SchedulerProvider,
    private val pre: PreferenceManager
) :
    AbstractViewModel() {
    val productListViewModel = MutableLiveData<ProductListResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun getProductList(
        service: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getProduct(
                service,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                brand,
                listing_type,
                listing_price,
                country,
                state,
                city,
                search_key
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productListViewModel.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

                }, {
                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            productListViewModel.value =
                                Gson().fromJson(error, ProductListResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    public fun saveUserDetail(user: UserList?) {
        pre.saveUserData(user)
    }

}