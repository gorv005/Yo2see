package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
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
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single
import org.json.JSONArray

class ProductListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ProductListRepository {


    override fun getProduct(
        service: String,
        userId: String,
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
        min_price: String,
        max_price: String, sort_type: String

    ): Single<ProductListInfoResponsePayload> {
        return restApi.getProductListing(
            service,
            userId,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            brand,
            listing_type,
            listing_price,
            country,
            state,
            city,
            search_key,
            event_type,
            min_price,
            max_price,
            sort_type
        )
    }

    override fun getJobProduct(
        service: String,
        userId: String,
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
        min_price: String,
        max_price: String, sort_type: String
    ): Single<JobListingResponsePayload> {
        return restApi.getJobProductListing(
            service,
            userId,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            brand,
            nType,
            listing_type,
            listing_price,
            country,
            state,
            city,
            search_key,
            event_type,
            min_price,
            max_price,
            sort_type
        )
    }

    override fun getEventListing(
        service: String,
        userId: String,
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
        min_price: String,
        max_price: String, sort_type: String
    ): Single<EventListingResponsePayload> {
        return restApi.getEventProductListing(
            service,
            userId,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            brand,
            nType,
            listing_type,
            listing_price,
            country,
            state,
            city,
            search_key,
            event_type,
            min_price,
            max_price,
            sort_type
        )
    }

    override fun getFavList(
        service: String,
        userId: String
    ): Single<ProductListInfoResponsePayload> {
        return restApi.getFavProductListing(
            service,
            userId
        )
    }

    override fun getUser(service: String, user_id: String): Single<LoginResponsePayload> {
        return restApi.getUserList(service, user_id)
    }

    override fun getProductDetails(
        service: String,
        user_id: String,
        id: String
    ): Single<ProductDetailResponsePayload> {
        return restApi.getProductDetails(service, user_id, id)
    }

    override fun getEventProductDetails(
        service: String,
        user_id: String,
        id: String
    ): Single<ProductDetailResponsePayload> {
        return restApi.getEventProductDetails(service, user_id, id)
    }

    override fun alterFavorites(
        service: String,
        user_id: String,
        list_id: String,
        fav_flag: Int
    ): Single<LoginResponsePayload> {
        return restApi.addToFavorites(service, user_id, list_id, fav_flag)
    }

    override fun postHistory(service: String, user_id: String): Single<PostHistoryResponsePayload> {
        return restApi.postHistory(service, user_id)
    }

    override fun deleteHistory(
        service: String,
        user_id: String,
        table_id: String,
        table_name: String
    ): Single<PostHistoryResponsePayload> {
        return restApi.deleteHistory(service, user_id, table_id, table_name)

    }

    override fun getComments(
        service: String,
        nType: String,
        user_id: String,
        event_id: String
    ): Single<CommentListResponse> {
        return restApi.getComments(service, nType, user_id, event_id)
    }

    override fun addComment(
        service: String,
        nType: String,
        user_id: String,
        event_id: String,
        parent_id: String,
        comment: String
    ): Single<CommentListResponse> {
        return restApi.addComment(service, nType, user_id, event_id,parent_id,comment)
    }


}