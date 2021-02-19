package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.model.product.ProductDetailResponsePayload
import com.dartmic.yo2see.model.product.ProductListResponsePayload
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single

class ProductListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ProductListRepository {


    override fun getProduct(
        service: String,
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
    ): Single<ProductListResponsePayload> {
        return restApi.getProductListing(
            service,
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

    override fun getProductDetails(
        service: String,
        user_id: String,
        id: String
    ): Single<ProductDetailResponsePayload> {
        return restApi.getProductDetails(service, user_id, id)
    }


}