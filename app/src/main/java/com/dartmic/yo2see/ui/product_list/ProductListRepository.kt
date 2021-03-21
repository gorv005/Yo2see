package com.gsa.ui.login


import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload

import io.reactivex.Single

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
        search_key: String
    ): Single<ProductListResponsePayload>

    fun getFavList(
        service: String,
        userId:String
    ): Single<ProductListResponsePayload>

    fun getProductDetails(
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

}