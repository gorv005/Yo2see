package com.gsa.ui.login


import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList

import io.reactivex.Single

interface LoginRepository {
    fun loginResponse(data: LoginRequest) : Single<LoginResponsePayload>
    fun saveUserData(token: String?, data: UserList, isRemember :Boolean)

}