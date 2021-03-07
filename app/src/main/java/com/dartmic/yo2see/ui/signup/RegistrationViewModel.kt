package com.dartmic.yo2see.ui.signup

import androidx.lifecycle.MutableLiveData
import com.dartmic.yo2see.base.AbstractViewModel
import com.dartmic.yo2see.base.SingleLiveEvent
import com.dartmic.yo2see.common.CommonBoolean
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.model.SearchEvent
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson
import com.gsa.ui.register.RegisterRepository
import retrofit2.HttpException

class RegistrationViewModel(
    private val registerRepository: RegisterRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val registerData = MutableLiveData<RegisterResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val otpData = MutableLiveData<OTPResponsePayload>()
    val otpVerify = MutableLiveData<OTPResponsePayload>()


    fun register(
        service: String,
        phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.register(
                service, phone, name, email, password, device_id, device_type, lat, longi
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

}