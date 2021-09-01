package com.gsa.ui.login


import com.dartmic.yo2see.model.comment.CommentListResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.my_history.PostHistoryResponsePayload
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.product.event.EventListingResponsePayload
import com.dartmic.yo2see.model.product.job.JobListingResponsePayload
import com.dartmic.yo2see.model.product_info.ProductListInfoResponsePayload

import io.reactivex.Single
import kotlinx.coroutines.Job
import org.json.JSONArray

interface ProductListRepository {
    fun getUser( service: String,
                 user_id: String
    ) : Single<LoginResponsePayload>

    fun getProduct(
        service: String,
        userId:String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price:String,
        max_price:String,
        sort_type:String
    ): Single<ProductListInfoResponsePayload>
    fun getJobProduct(
        service: String,
        userId:String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        nType: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price:String,
        max_price:String,
        sort_type:String
    ): Single<JobListingResponsePayload>

    fun getEventListing(
        service: String,
        userId:String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        nType: String,
        listing_type: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        search_key: String,
        event_type: JSONArray,
        min_price:String,
        max_price:String,
        sort_type:String
    ): Single<EventListingResponsePayload>

    fun getFavList(
        service: String,
        userId:String
    ): Single<ProductListInfoResponsePayload>

    fun getProductDetails(
        service: String,
        user_id: String,
        id: String
    ): Single<ProductDetailResponsePayload>

    fun getEventProductDetails(
        service: String,
        user_id: String,
        id: String
    ): Single<ProductDetailResponsePayload>

    fun alterFavorites(
        service: String,
        user_id: String,
        id: String,
        fav_flag:Int
    ): Single<LoginResponsePayload>

    fun postHistory(
        service: String,
        user_id: String
    ): Single<PostHistoryResponsePayload>

    fun deleteHistory(
        service: String,
        user_id: String,
        table_id: String,
        table_name: String
    ): Single<PostHistoryResponsePayload>


    fun getComments(
        service: String,
        nType: String,
        user_id: String,
        event_id: String
    ): Single<CommentListResponse>


    fun addComment(
        service: String,
        nType: String,
        user_id: String,
        event_id: String,
        parent_id: String,
        comment: String
    ): Single<CommentListResponse>

}