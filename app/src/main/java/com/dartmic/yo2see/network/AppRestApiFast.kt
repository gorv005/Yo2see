package com.dartmic.yo2see.network

import com.dartmic.yo2see.model.Category_sub_subTosub.CategoryDataResponsePayload
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.categories.CategoriesResponse
import com.dartmic.yo2see.model.categories.FeatureListResponse
import com.dartmic.yo2see.model.comment.CommentListResponse
import com.dartmic.yo2see.model.list_dropdown.ListOfDropDownResponse
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.my_history.PostHistoryResponsePayload
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.product.event.EventListingResponsePayload
import com.dartmic.yo2see.model.product.job.JobListingResponsePayload
import com.dartmic.yo2see.model.product_info.ProductListInfoResponsePayload
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
        @Field("user_name") username: String,
        @Field("password") password: String,
        @Field("device_type") device_type: String,
        @Field("device_id") device_id: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("uid") uid: String
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
        @Field("usertype") usertype: String,
        @Field("uid") uid: String

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
        @Field("latitude") state: String,
        @Field("longitude") city: String,
        @Field("search_key") search_key: String,
        @Field("event_type") event_type: JSONArray,
        @Field("min_price") min_price: String,
        @Field("max_price") max_price: String,
        @Field("sort_type") sort_type: String

    ): Single<ProductListInfoResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_JOB_DATA_API)
    fun getJobProductListing(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("brand") brand: String,
        @Field("ntype") ntype: String,
        @Field("listing_type") listing_type: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("latitude") state: String,
        @Field("longitude") city: String,
        @Field("search_key") search_key: String,
        @Field("event_type") event_type: JSONArray,
        @Field("min_price") min_price: String,
        @Field("max_price") max_price: String,
        @Field("sort_type") sort_type: String

    ): Single<JobListingResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_JOB_DATA_API)
    fun getEventProductListing(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("brand") brand: String,
        @Field("ntype") ntype: String,
        @Field("listing_type") listing_type: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("latitude") state: String,
        @Field("longitude") city: String,
        @Field("search_key") search_key: String,
        @Field("event_type") event_type: JSONArray,
        @Field("min_price") min_price: String,
        @Field("max_price") max_price: String,
        @Field("sort_type") sort_type: String

    ): Single<EventListingResponsePayload>

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
    @POST(Config.Endpoints.GET_GEN_DATA_API)
    fun getTypelist(
        @Field("service") service: String, @Field("gen_type") gen_type: String
    ): Single<ListOfDropDownResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_API)
    fun addProdcut(
        @Field("service") service: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("brand") brand: String,
        @Field("is_rent") is_rent: String,
        @Field("is_barter") is_barter: String,
        @Field("is_sell") is_sell: String,
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
    @POST(Config.Endpoints.PRODUCT_ADD_JOB_API)
    fun addJob(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("open_to_deliver") open_to_deliver: String,
        @Field("km_range") km_range: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("job_skills") job_skills: String,
        @Field("job_level") job_level: String,
        @Field("job_type") job_type: String,
        @Field("job_responsibility") job_responsibility: String

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_EVENT_API)
    fun addEvent(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("event_from") event_from: String,
        @Field("event_to") event_to: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("event_type") event_type: String,
    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_BUSINESS)
    fun addBusiness(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("business_type") business_type: String,
        @Field("photos_array") photos_array: JSONArray

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_BLOG)
    fun addBlog(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("blog_category") blog_category: String,
        @Field("photos_array") photos_array: JSONArray

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_POEM)
    fun addPoem(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("poem_type") poem_type: String
    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_STORY)
    fun addStory(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("story_type") poem_type: String
    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_FREELANCE)
    fun addFreelance(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("freelance_type") poem_type: String
    ): Single<AddProdcutResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_LOCAL_SERVICE)
    fun addLocalService(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("service_type") poem_type: String
    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_DETAILS_DATA_API)
    fun getProductDetails(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("id") id: String
    ): Single<ProductDetailResponsePayload>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_DETAILS_EVENT_DATA_API)
    fun getEventProductDetails(
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
    ): Single<ProductListInfoResponsePayload>


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

    @FormUrlEncoded
    @POST(Config.Endpoints.FEEDBACK)
    fun sendFeedback(
        @Field("service") user_id: String,
        @Field("to_user_id") to_user_id: String,
        @Field("from_user_id") from_user_id: String, @Field("rating") rating: String

    ): Single<UserInforesponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_BUSINESS_FOR_SALE)
    fun addBusinessForSale(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("business_for_sale_type") business_type: String,
        @Field("photos_array") photos_array: JSONArray

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_UNCATEGORIZED)
    fun addUncategorized(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("uncategorized_type") poem_type: String
    ): Single<AddProdcutResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_VOLUNTERRING)
    fun addVolunteering(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("valunteering_type") poem_type: String
    ): Single<AddProdcutResponse>

    @FormUrlEncoded
    @POST(Config.Endpoints.PRODUCT_ADD_FORUM)
    fun addForum(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("category_id") category_id: String,
        @Field("sub_cat_id") sub_cat_id: String,
        @Field("sub_to_sub_cat_id") sub_to_sub_cat_id: String,
        @Field("listing_price") listing_price: String,
        @Field("country") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("pincode") pincode: String,
        @Field("address") address: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("publish_datetime") publish_datetime: String,
        @Field("expiry_datetime") expiry_datetime: String,
        @Field("longitude") longitude: String,
        @Field("latitude") latitude: String,
        @Field("forum_type") business_type: String,
        @Field("photos_array") photos_array: JSONArray

    ): Single<AddProdcutResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.POST_HISTORY)
    fun postHistory(
        @Field("service") service: String,
        @Field("user_id") user_id: String


    ): Single<PostHistoryResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.DELETE_HISTORY)
    fun deleteHistory(
        @Field("service") service: String,
        @Field("user_id") user_id: String,
        @Field("Table_Id") Table_Id: String,
        @Field("TableName") TableName: String


    ): Single<PostHistoryResponsePayload>


    @FormUrlEncoded
    @POST(Config.Endpoints.COMMENT_LIST)
    fun getComments(
        @Field("service") service: String,
        @Field("nType") nType: String,
        @Field("user_id") user_id: String,
        @Field("event_id") event_id: String


    ): Single<CommentListResponse>


    @FormUrlEncoded
    @POST(Config.Endpoints.ADD_COMMENT)
    fun addComment(
        @Field("service") service: String,
        @Field("nType") nType: String,
        @Field("user_id") user_id: String,
        @Field("event_id") event_id: String,
        @Field("parent_id") parent_id: String,
        @Field("comment") comment: String

    ): Single<CommentListResponse>
}

