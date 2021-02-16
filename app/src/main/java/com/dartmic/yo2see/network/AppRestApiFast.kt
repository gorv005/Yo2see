package com.dartmic.yo2see.network

import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryDataResponsePayload
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.utils.Config
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AppRestApiFast {


    @FormUrlEncoded
    @POST(Config.Endpoints.LOGIN_API)
    fun login(
        @Field("service") service: String,
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
        @Field("service") service: String, @Field("type") type: String

    ): Single<CategoriesResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.CATEGORIES_DATA_API)
    fun getCategoriesData(
        @Field("service") service: String, @Field("type") type: String

    ): Single<CategoryDataResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.FEATURE_API)
    fun getFeatureList(
        @Field("service") service: String
    ): Single<FeatureListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_DATA_API)
    fun getProductListing(
        @Field("service") service: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("brand") brand: String,
        @Field("listing_type") listing_type: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("search_key") search_key: String
    ): Single<ProductListResponsePayload>


    @Multipart
    @POST(Config.Endpoints.UPLOAD_IMAGE_API)
    fun uploadImage(
        @Part("service") service: RequestBody,
        @Part("user_id") user_id: RequestBody,
        @Part("type") type: RequestBody,
        @Part  images:MultipartBody.Part

        ): Single<UploadImageResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_DATA_API)
    fun addProdcut(
        @Field("service") service: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("brand") brand: String,
        @Field("listing_type") listing_type: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("item_condition") item_condition: String,
        @Field("negotiation_type") negotiation_type: String,
        @Field("open_to_deliver") open_to_deliver: String,
        @Field("km_range") km_range: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("user_id") user_id: String,
        @Field("photos_array") photos_array: List<ImagePathResponse>,
        @Field("rent_type_array") rent_type_array:  List<RentTypeResponse>

    ): Single<AddProdcutResponse>

}

