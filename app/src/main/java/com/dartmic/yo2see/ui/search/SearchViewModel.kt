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
import com.dartmic.yo2see.utils.Config
import com.dartmic.yo2see.utils.Logger
import com.google.gson.Gson

import retrofit2.HttpException

class SearchViewModel(
    private val loginRepository: LoginRepository,
    private val scheduler: SchedulerProvider,
    private val pre: PreferenceManager
) :
    AbstractViewModel() {
    val loginData = MutableLiveData<LoginResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val socialLoginData = MutableLiveData<LoginResponsePayload>()

    fun socialLogin(
        service: String, user_name: String, email: String,phone: String,device_id: String,device_type: String,longitude: String
        ,latitude: String,family_name: String,given_name: String,picture: String,locale: String,login_from: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            loginRepository.socialLogin(service, user_name, email, phone, device_id, device_type, longitude, latitude, family_name, given_name, picture, locale, login_from)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    socialLoginData.value = it
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

                            socialLoginData.value =
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


    fun login(
        service: String,
        userName: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = LoginRequest(service, userName, password, device_id, device_type, lat, longi,"")

        launch {
            loginRepository.loginResponse(user)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    loginData.value = it
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

                            loginData.value =
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

    public fun saveUserDetail(user: UserList?) {
        pre.saveUserData(user)
    }

}