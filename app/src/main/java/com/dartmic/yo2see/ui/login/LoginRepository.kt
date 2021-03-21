package com.gsa.ui.login


import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList

import io.reactivex.Single

interface LoginRepository {
    fun loginResponse(data: LoginRequest) : Single<LoginResponsePayload>
    fun saveUserData(token: String?, data: UserList, isRemember :Boolean)
    fun socialLogin(service: String, user_name: String, email: String,phone: String,device_id: String,device_type: String,longitude: String
    ,latitude: String,family_name: String,given_name: String,picture: String,locale: String,login_from: String):Single<LoginResponsePayload>

}