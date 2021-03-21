package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single

class LoginRepositoryImpl(private val restApi: AppRestApiFast, private val pre: PreferenceManager) : LoginRepository {
    override fun saveUserData(token: String?, data: UserList, isRemember :Boolean) {
      //  pre.loginUser(token,data,isRemember)
    }

    override fun socialLogin(
        service: String,
        user_name: String,
        email: String,
        phone: String,
        device_id: String,
        device_type: String,
        longitude: String,
        latitude: String,
        family_name: String,
        given_name: String,
        picture: String,
        locale: String,
        login_from: String
    ): Single<LoginResponsePayload> {
        return restApi.socialLogin(service,user_name,email,phone,device_id,device_type,longitude,latitude,family_name,given_name,picture,locale,login_from)
    }


    override fun loginResponse(data: LoginRequest): Single<LoginResponsePayload> {
        return restApi.login(data.service,data.user_name,data.password,data.device_type,data.device_id,data.longitude,data.latitude)
    }






}