package com.dartmic.yo2see.network

import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.utils.Config
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppRestApiFast {

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

