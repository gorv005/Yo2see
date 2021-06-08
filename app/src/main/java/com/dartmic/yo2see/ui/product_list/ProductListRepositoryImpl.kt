package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.model.product_info.ProductListInfoResponsePayload
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single

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
        search_key: String
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
            search_key
        )
    }

    override fun getFavList(service: String, userId: String): Single<ProductListInfoResponsePayload> {
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

    override fun alterFavorites(
        service: String,
        user_id: String,
        list_id: String,
        fav_flag: Int
    ): Single<LoginResponsePayload> {
        return restApi.addToFavorites(service, user_id, list_id, fav_flag)
    }


}