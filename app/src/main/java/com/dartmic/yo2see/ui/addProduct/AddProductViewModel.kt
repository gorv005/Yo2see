package com.gsa.ui.login

import androidx.lifecycle.MutableLiveData
import com.dartmic.yo2see.base.AbstractViewModel
import com.dartmic.yo2see.base.SingleLiveEvent
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.SearchEvent
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray

import retrofit2.HttpException
import java.util.ArrayList

class AddProductViewModel(
    private val addProductRepository: AddProductRepository,
    private val scheduler: SchedulerProvider,
    private val pre: PreferenceManager
) :
    AbstractViewModel() {
    val uploadImageModel = MutableLiveData<UploadImageResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val addProductModel = MutableLiveData<AddProdcutResponse>()

    fun uploadImage(
        service: RequestBody,
        user_id: RequestBody,
        type: RequestBody,
        images: MultipartBody.Part
    ) {
     //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.uploadImages(service, user_id, type, images)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    uploadImageModel.value = it
               /*     searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)*/

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

                            uploadImageModel.value =
                                Gson().fromJson(error, UploadImageResponse::class.java)
                           /* searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
*/
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun addProduct(
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
        pincode: String,
        address: String,
        title: String,
        description: String,
        item_condition: String,
        negotiation_type: String,
        open_to_deliver: String,
        km_range: String,
        publish_datetime: String,
        expiry_datetime: String,
        user_id: String,
        photos_array: JSONArray,
        rent_type_array: JSONArray,
        longitude: String,
        latitude: String,
        barter_text: String,
        barter_exchange_text: String,
        barter_product_title: String,
        barter_product_desc: String,
        barter_additional_text: String,
        rent_product_detail: String,
        rent_terms_and_condition: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addProduct(
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
                pincode,
                address,
                title,
                description,
                item_condition,
                negotiation_type,
                open_to_deliver,
                km_range,
                publish_datetime,
                expiry_datetime,
                user_id,
                photos_array,
                rent_type_array,
                longitude, latitude, barter_text, barter_exchange_text, barter_product_title, barter_product_desc, barter_additional_text, rent_product_detail, rent_terms_and_condition
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addProductModel.value = it
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

                            addProductModel.value =
                                Gson().fromJson(error, AddProdcutResponse::class.java)
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