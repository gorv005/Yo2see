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
import com.dartmic.yo2see.model.list_dropdown.ListOfDropDownResponse
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
    val userViewModel = MutableLiveData<LoginResponsePayload>()

    val listOFJobType = MutableLiveData<ListOfDropDownResponse>()

    val listOFJobLevel = MutableLiveData<ListOfDropDownResponse>()
    val addJobProductModel = MutableLiveData<AddProdcutResponse>()

    fun getUser(
        service: String,
        user_id: String
    ) {
        //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.getUser(service, user_id)
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

    fun getJobType(
        service: String,
        gen_type: String
    ) {
        //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.getType(service, gen_type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    listOFJobType.value = it
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

                            listOFJobType.value =
                                Gson().fromJson(error, ListOfDropDownResponse::class.java)
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

    fun getJobLevel(
        service: String,
        gen_type: String
    ) {
        //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.getType(service, gen_type)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    listOFJobLevel.value = it
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

                            listOFJobLevel.value =
                                Gson().fromJson(error, ListOfDropDownResponse::class.java)
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
        isRent: String,
        isBarter: String,
        isSell: String,
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
                isRent,
                isBarter,
                isSell,
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
                longitude,
                latitude,
                barter_text,
                barter_exchange_text,
                barter_product_title,
                barter_product_desc,
                barter_additional_text,
                rent_product_detail,
                rent_terms_and_condition
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


    fun addJobProduct(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        open_to_deliver: String,
        km_range: String,
        publish_datetime: String,
        longitude: String,
        latitude: String,
        job_skills: String,
        job_level: String,
        job_type: String,
        job_responsibility: String

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addJob(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                open_to_deliver,
                km_range,
                publish_datetime,
                longitude,
                latitude,
                job_skills,
                job_level,
                job_type,
                job_responsibility
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addEvent(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        event_from: String,
        event_to: String,
        longitude: String,
        latitude: String,
        event_type: String

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addEvent(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                event_from,
                event_to,
                longitude,
                latitude,
                event_type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addBusiness(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addBusiness(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                business_type,
                image_type_array
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addBlog(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addBlog(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                business_type,
                image_type_array
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addPoem(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addPoem(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addStory(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addStory(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addFreelance(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addFreelance(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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


    fun addLocalService(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addLocalService(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addBusinessForSale(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addBusinessForSale(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                business_type,
                image_type_array
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    fun addUncategorized(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addUncategorized(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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
    fun addVolunteering(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addVolunteering(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                type
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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
    fun addForum(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray

    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            addProductRepository.addForum(
                service,
                user_id,
                category_id,
                sub_cat_id,
                sub_to_sub_cat_id,
                listing_price,
                country,
                state,
                city,
                pincode,
                address,
                title,
                description,
                publish_datetime,
                expiry_datetime,
                longitude,
                latitude,
                business_type,
                image_type_array
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    addJobProductModel.value = it
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

                            addJobProductModel.value =
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

    public fun getUserID(): String? {
        return pre.getLoggedInUserId()
    }



}