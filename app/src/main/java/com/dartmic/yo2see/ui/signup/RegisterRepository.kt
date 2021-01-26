package com.gsa.ui.register



import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import io.reactivex.Single

interface RegisterRepository {
    fun register(
        service: String, phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String
    ): Single<RegisterResponsePayload>



}