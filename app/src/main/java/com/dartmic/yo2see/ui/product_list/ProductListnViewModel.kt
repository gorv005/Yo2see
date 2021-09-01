package com.gsa.ui.login

import androidx.lifecycle.MutableLiveData
import com.dartmic.yo2see.base.AbstractViewModel
import com.dartmic.yo2see.base.SingleLiveEvent
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.SearchEvent
import com.dartmic.yo2see.model.comment.CommentListResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.my_history.PostHistoryResponsePayload
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.product.event.EventListingResponsePayload
import com.dartmic.yo2see.model.product.job.JobListingResponsePayload
import com.dartmic.yo2see.model.product_info.ProductListInfoResponsePayload
import com.dartmic.yo2see.model.profile.UserInforesponse
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson
import org.json.JSONArray

import retrofit2.HttpException

class ProductListnViewModel(
    private val productListRepository: ProductListRepository,
    private val scheduler: SchedulerProvider,
    private val pre: PreferenceManager
) :
    AbstractViewModel() {
    val productListViewModel = MutableLiveData<ProductListInfoResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val productDetailsViewModel = MutableLiveData<ProductDetailResponsePayload>()
    val userViewModel = MutableLiveData<LoginResponsePayload>()
    val favViewModel = MutableLiveData<LoginResponsePayload>()
    val productJobListViewModel = MutableLiveData<JobListingResponsePayload>()
    val productEventListViewModel = MutableLiveData<EventListingResponsePayload>()
    val productHistory1 = MutableLiveData<PostHistoryResponsePayload>()
    val deleteHistory = MutableLiveData<PostHistoryResponsePayload>()
    val commentListViewModel = MutableLiveData<CommentListResponse>()
    val addCommentListViewModel = MutableLiveData<CommentListResponse>()

    fun getUser(
        service: String,
        user_id: String
    ) {
        //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getUser(service, user_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userViewModel.value = it
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

                            userViewModel.value =
                                Gson().fromJson(error, LoginResponsePayload::class.java)
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


    fun getFavProductList(
        service: String,
        userId: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getFavList(
                service,
                userId
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
                                Gson().fromJson(error, ProductListInfoResponsePayload::class.java)
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

    fun getProductList(
        service: String,
        userId: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price: String,
        max_price: String,
        sort_type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getProduct(
                service,
                userId,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                brand,
                listing_type,
                listing_price,
                country,
                state,
                city,
                search_key,
                event_type,
                min_price,
                max_price,
                sort_type
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
                                Gson().fromJson(error, ProductListInfoResponsePayload::class.java)
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


    fun getJobProductList(
        service: String,
        userId: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        nType: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price: String,
        max_price: String,
        sort_type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getJobProduct(
                service,
                userId,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                brand,
                nType,
                listing_type,
                listing_price,
                country,
                state,
                city,
                search_key,
                event_type,
                min_price,
                max_price,
                sort_type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productJobListViewModel.value = it
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

                            productJobListViewModel.value =
                                Gson().fromJson(error, JobListingResponsePayload::class.java)
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

    fun getEventProductList(
        service: String,
        userId: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        nType: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price: String,
        max_price: String,
        sort_type: String

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getEventListing(
                service,
                userId,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                brand,
                nType,
                listing_type,
                listing_price,
                country,
                state,
                city,
                search_key,
                event_type,
                min_price,
                max_price,
                sort_type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productEventListViewModel.value = it
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

                            productEventListViewModel.value =
                                Gson().fromJson(error, EventListingResponsePayload::class.java)
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

    fun addAndRemoveToFavorites(
        service: String,
        user_id: String,
        id: String,
        fav_flag: Int
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.alterFavorites(
                service,
                user_id,
                id,
                fav_flag
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    favViewModel.value = it
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

                            favViewModel.value =
                                Gson().fromJson(error, LoginResponsePayload::class.java)
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

    fun getProductDetails(
        service: String,
        user_id: String,
        id: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getProductDetails(
                service,
                user_id,
                id
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productDetailsViewModel.value = it
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

                            productDetailsViewModel.value =
                                Gson().fromJson(error, ProductDetailResponsePayload::class.java)
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


    fun getEventProductDetails(
        service: String,
        user_id: String,
        id: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getEventProductDetails(
                service,
                user_id,
                id
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productDetailsViewModel.value = it
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

                            productDetailsViewModel.value =
                                Gson().fromJson(error, ProductDetailResponsePayload::class.java)
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

    fun getPostHistory(
        service: String,
        user_id: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.postHistory(
                service,
                user_id
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productHistory1.value = it
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

                            productHistory1.value =
                                Gson().fromJson(error, PostHistoryResponsePayload::class.java)
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


    fun deleteHistory(
        service: String,
        user_id: String,
        table_id: String,
        table_name: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.deleteHistory(
                service,
                user_id,
                table_id,
                table_name
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    deleteHistory.value = it
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

                            deleteHistory.value =
                                Gson().fromJson(error, PostHistoryResponsePayload::class.java)
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


    fun getComments(
        service: String,
        nType: String,
        user_id: String,
        event_id: String
    ) {
        // searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.getComments(
                service,
                nType,
                user_id,
                event_id
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    commentListViewModel.value = it
                    /*    searchEvent.value =
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

                            commentListViewModel.value =
                                Gson().fromJson(error, CommentListResponse::class.java)
                            /*   searchEvent.value =
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


    fun addComments(
        service: String,
        nType: String,
        user_id: String,
        event_id: String,
        parent_id: String,
        comment: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            productListRepository.addComment(
                service,
                nType,
                user_id,
                event_id,
                parent_id,
                comment
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addCommentListViewModel.value = it
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

                            addCommentListViewModel.value =
                                Gson().fromJson(error, CommentListResponse::class.java)
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

    public fun getUserID(): String? {
        return pre.getLoggedInUserId()
    }

    public fun getUserImage(): String? {
        return pre.getUserImage()
    }

    public fun getLoggedInUserName(): String? {
        return pre.getLoggedInUserName()
    }

    public fun getEmail(): String? {
        return pre.getLoggedInUserEmail()
    }

    public fun getUserPassword(): String? {
        return pre.getLoggedInUserPassword()
    }

    public fun getLatitude(): String? {
        return pre.getLatitude()
    }

    public fun getLongitude(): String? {
        return pre.getLongitude()
    }
}