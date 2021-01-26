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


    override fun loginResponse(data: LoginRequest): Single<LoginResponsePayload> {
        return restApi.login(data.service,data.user_name,data.password,data.device_type,data.device_id,data.longitude,data.latitude)
    }






}