package com.dartmic.yo2see.network

import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryDataResponsePayload
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.profile.UserInforesponse
import com.dartmic.yo2see.model.signUp.OTPResponsePayload
import com.dartmic.yo2see.model.signUp.RegisterResponsePayload
import com.dartmic.yo2see.utils.Config
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.http.*
import java.util.ArrayList

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
    @POST(Config.Endpoints.SOCIAL_LOGIN_API)
    fun socialLogin(
        @Field("service") service: String,
        @Field("user_name") username: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("device_id") device_id: String,
        @Field("device_type") device_type: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("family_name") family_name: String,
        @Field("given_name") given_name: String,
        @Field("picture") picture: String,
        @Field("locale") locale: String,
        @Field("login_from") login_from: String
    )
            : Single<LoginResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.SIGN_UP_API)
    fun register(
        @Field("service") service: String,
        @Field("phone") phone: String,
        @Field("user_name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("usertype") usertype: String
    ): Single<RegisterResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.ALL_TYPE_API)
    fun getCategories(
        @Field("service") service: String, @Field("type") type: String

    ): Single<CategoryDataResponsePayload>

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
        @Field("user_id") user_id: String,
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
        @Part images: MultipartBody.Part

    ): Single<UploadImageResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.GET_USER_DATA_API)
    fun getUserList(
        @Field("service") service: String, @Field("user_id") user_id: String
    ): Single<LoginResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_API)
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
        @Field("photos_array") photos_array: JSONArray,
        @Field("rent_type_array") rent_type_array: JSONArray,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("barter_text") barter_text: String,
        @Field("barter_exchange_text") barter_exchange_text: String,
        @Field("barter_product_title") barter_product_title: String,
        @Field("barter_product_desc") barter_product_desc: String,
        @Field("barter_additional_text") barter_additional_text: String,
        @Field("rent_product_detail") rent_product_detail: String,
        @Field("rent_terms_and_condition") rent_terms_and_condition: String

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_DETAILS_DATA_API)
    fun getProductDetails(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("id") id: String
    ): Single<ProductDetailResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.GET_OTP)
    fun getOTP(
        @Field("service") service: String,
        @Field("phone") phone: String
    ): Single<OTPResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.VERIFY_OTP)
    fun verifyOTP(
        @Field("service") service: String,
        @Field("phone") phone: String,
        @Field("otp") otp: String

    ): Single<OTPResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.GET_USER)
    fun getUser(
        @Field("service") service: String,
        @Field("user_id") user_id: String
    ): Single<UserInforesponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.REMOVE_ACCOUNT)
    fun removeAccount(
        @Field("service") service: String,
        @Field("user_id") user_id: String
    ): Single<UserInforesponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.UPDATE_USER_API)
    fun updateUser(
        @Field("service") service: String, @Field("user_id") user_id: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") password: String,
        @Field("picture") device_type: String
    ): Single<UserInforesponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.ADD_TO_FAVORITES)
    fun addToFavorites(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("list_id") list_id: String,
    @Field("fav_flag") fav_flag: Int


    ): Single<LoginResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_FAV_LIST_API)
    fun getFavProductListing(
        @Field("service") service: String,
        @Field("user_id") user_id: String
    ): Single<ProductListResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.CHANGE_PASSWORD)
    fun changePassword(
        @Field("user_id") user_id: String,
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String

    ): Single<UserInforesponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.RESEND_EMAIL)
    fun resendEmail(
        @Field("service") service: String,
        @Field("user_id") user_id: String
    ): Single<UserInforesponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.FORGOT_PASSWORD)
    fun forgotPassword(
        @Field("service") user_id: String,
        @Field("user_email") user_email: String
    ): Single<UserInforesponse>

}

