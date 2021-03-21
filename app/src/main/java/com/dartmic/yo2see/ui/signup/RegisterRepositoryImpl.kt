package com.gsa.ui.register


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.profile.UserInforesponse
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.network.AppRestApiFast
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : RegisterRepository {
    override fun uploadImages(
        service: RequestBody,
        user_id: RequestBody,
        type: RequestBody,
        images: MultipartBody.Part
    ): Single<UploadImageResponse> {
        return restApi.uploadImage(service, user_id, type, images)
    }

    override fun register(
        service: String,
        phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String,
        user_type:String
    ): Single<RegisterResponsePayload> {
        return restApi.register(
            service,
            phone,
            name,
            email,
            password,
            device_id,
            device_type,
            lat,
            longi,
            user_type
        )
    }

    override fun updateUser(
        service: String,
        user_id: String,
        name: String,
        email: String,
        phone: String,
        picture: String
    ): Single<UserInforesponse> {
        return restApi.updateUser(service, user_id, name, email, phone, picture)
    }

    override fun getOtp(service: String, phone: String): Single<OTPResponsePayload> {
        return restApi.getOTP(service, phone)
    }

    override fun verifyOtp(
        service: String,
        phone: String,
        otp: String
    ): Single<OTPResponsePayload> {
        return restApi.verifyOTP(service, phone, otp)
    }

    override fun removeAccount(service: String, user_id: String): Single<UserInforesponse> {
        return restApi.removeAccount(service, user_id)
    }

    override fun getUser(service: String, userId: String): Single<UserInforesponse> {
        return restApi.getUser(service, userId)
    }

    override fun changePassword(
        service: String,
        user_id: String,
        old_password: String,
        new_password: String
    ): Single<UserInforesponse> {
        return restApi.changePassword( user_id,old_password,new_password)
    }


}