package com.gsa.ui.register



import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.network.AppRestApiFast
import io.reactivex.Single

class RegisterRepositoryImpl(private val restApi: AppRestApiFast, private val pre: PreferenceManager) : RegisterRepository {

    override fun register(
        service: String,
        phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String
    ): Single<RegisterResponsePayload> {
        return restApi.register(service,phone,name,email,password,device_id,device_type,lat,longi)
    }

    override fun getOtp(service: String, phone: String): Single<OTPResponsePayload> {
        return restApi.getOTP(service,phone)
    }

    override fun verifyOtp(
        service: String,
        phone: String,
        otp: String
    ): Single<OTPResponsePayload> {
        return restApi.getOTP(service,phone)
    }


}