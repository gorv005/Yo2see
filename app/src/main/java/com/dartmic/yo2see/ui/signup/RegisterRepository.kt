package com.gsa.ui.register


import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.profile.UserInforesponse
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RegisterRepository {

    fun uploadImages(service: RequestBody,
                     user_id: RequestBody,
                     type: RequestBody,
                     images: MultipartBody.Part
    ) : Single<UploadImageResponse>
    fun register(
        service: String, phone: String,
        name: String,
        email: String,
        password: String,
        device_id: String,
        device_type: String,
        lat: String,
        longi: String,
        user_type:String,
        uid:String

    ): Single<RegisterResponsePayload>
    fun updateUser(
        service: String,
        user_id: String,
        name: String,
        email: String,
        phone: String,
        picture: String
    ): Single<UserInforesponse>

    fun getOtp(
        service: String, phone: String
    ): Single<OTPResponsePayload>

    fun verifyOtp(
        service: String, phone: String,otp:String
    ): Single<OTPResponsePayload>

    fun resendEmail(
        service: String, user_id: String
    ): Single<UserInforesponse>

    fun removeAccount(
        service: String, user_id: String
    ): Single<UserInforesponse>
    fun getUser(
        service: String, userId: String
    ): Single<UserInforesponse>

    fun changePassword(
        service: String,
        user_id: String, old_password: String, new_password: String
    ): Single<UserInforesponse>

    fun forgotPassword(
        service: String,
        email:String
    ): Single<UserInforesponse>

    fun sendFeedback(
        service: String,
        to_user_id: String,
        from_user_id: String,
        rating: String
    ): Single<UserInforesponse>
}