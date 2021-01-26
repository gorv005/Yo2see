package com.dartmic.yo2see.network

import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.utils.Config
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppRestApiFast {


    @FormUrlEncoded
    @POST(Config.Endpoints.LOGIN_API)
    fun login(@Field("service") service: String,
        @Field("user_name") username: String, @Field("password") password: String,
        @Field("device_type") device_type: String, @Field("device_id") device_id: String,
              @Field("longitude") longitude: String, @Field("latitude") latitude: String
    )
            : Single<LoginResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.SIGN_UP_API)
    fun register(
        @Field("service") service: String, @Field("phone") phone: String,
        @Field("user_name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String, @Field("device_id") device_id: String,
        @Field("longitude") longitude: String, @Field("latitude") latitude: String
    ): Single<RegisterResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.CATEGORIES_API)
    fun getCategories(
        @Field("service") service: String
    ): Single<CategoriesResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.FEATURE_API)
    fun getFeatureList(
        @Field("service") service: String
    ): Single<FeatureListResponse>
}

