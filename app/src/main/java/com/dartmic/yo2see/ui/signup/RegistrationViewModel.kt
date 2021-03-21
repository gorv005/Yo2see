package com.dartmic.yo2see.ui.signup

import androidx.lifecycle.MutableLiveData
import com.dartmic.yo2see.base.AbstractViewModel
import com.dartmic.yo2see.base.SingleLiveEvent
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.SearchEvent
import com.dartmic.yo2see.model.profile.UserInforesponse
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson
import com.gsa.ui.register.RegisterRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class RegistrationViewModel(
    private val registerRepository: RegisterRepository,
    private val scheduler: SchedulerProvider,     private val pre: PreferenceManager

) :
    AbstractViewModel() {
    val registerData = MutableLiveData<RegisterResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val otpData = MutableLiveData<OTPResponsePayload>()
    val otpVerify = MutableLiveData<OTPResponsePayload>()
    val userInforesponseViewModel = MutableLiveData<UserInforesponse>()
    val userInfoSaveViewModel = MutableLiveData<UserInforesponse>()

    val uploadImageModel = MutableLiveData<UploadImageResponse>()
    val removeAccountModel = MutableLiveData<UserInforesponse>()
    val changePasswordModel = MutableLiveData<UserInforesponse>()

    fun uploadImage(
        service: RequestBody,
        user_id: RequestBody,
        type: RequestBody,
        images: MultipartBody.Part
    ) {
        //   searchEvent.value = SearchEvent(isLoading = true)


        launch {
            registerRepository.uploadImages(service, user_id, type, images)
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

    fun updateUser(
        service: String,
        user_id: String,
        name: String,
        email: String,
        phone: String,
        picture: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.updateUser(
                service, user_id, name, email, phone, picture
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userInfoSaveViewModel.value = it
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

                            userInfoSaveViewModel.value =
                                Gson().fromJson(error, UserInforesponse::class.java)
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

    fun register(
        service: String,
        phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String,
        userType: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.register(
                service, phone, name, email, password, device_id, device_type, lat, longi,userType
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    registerData.value = it
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

                            registerData.value =
                                Gson().fromJson(error, RegisterResponsePayload::class.java)
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

    fun getOTP(
        service: String,
        phone: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.getOtp(
                service, phone
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    otpData.value = it
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

                            otpData.value =
                                Gson().fromJson(error, OTPResponsePayload::class.java)
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
    fun verifyOtp(
        service: String,
        phone: String,
        otp:String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.verifyOtp(
                service, phone,otp
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    otpVerify.value = it
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

                            otpVerify.value =
                                Gson().fromJson(error, OTPResponsePayload::class.java)
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


    fun getUser(
        service: String,
        userId: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.getUser(
                service, userId
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userInforesponseViewModel.value = it
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

                            userInforesponseViewModel.value =
                                Gson().fromJson(error, UserInforesponse::class.java)
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





    fun changePassword(
        service: String,
        user_id: String, old_password: String, new_password: String) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.changePassword(
                service,user_id,old_password,new_password
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    changePasswordModel.value = it
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

                            changePasswordModel.value =
                                Gson().fromJson(error, UserInforesponse::class.java)
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

    fun removeAccount(
        service: String,
        user_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.removeAccount(
                service,user_id
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    removeAccountModel.value = it
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

                            removeAccountModel.value =
                                Gson().fromJson(error, UserInforesponse::class.java)
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

    public fun getUserID() :String?{
        return pre.getLoggedInUserId()
    }
}