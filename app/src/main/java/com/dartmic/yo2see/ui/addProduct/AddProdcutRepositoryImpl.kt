package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import java.util.ArrayList

class AddProdcutRepositoryImpl(private val restApi: AppRestApiFast, private val pre: PreferenceManager) : AddProductRepository {


    override fun uploadImages(
        service: RequestBody,
        user_id: RequestBody,
        type: RequestBody,
        images: MultipartBody.Part
    ): Single<UploadImageResponse> {
        return restApi.uploadImage(service, user_id, type, images)
    }

    override fun addProduct(
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
        pincode: String,
        address: String,
        title: String,
        description: String,
        item_condition: String,
        negotiation_type: String,
        open_to_deliver: String,
        km_range: String,
        publish_datetime: String,
        expiry_datetime: String,
        user_id: String,
        photos_array: JSONArray,
        rent_type_array: JSONArray,
        longitude: String,
        latitude: String,
        barter_text: String,
        barter_exchange_text: String,
        barter_product_title: String,
        barter_product_desc: String,
        barter_additional_text: String,
        rent_product_detail: String,
        rent_terms_and_condition: String
    ): Single<AddProdcutResponse> {
        return restApi.addProdcut(service, category_id, sub_cat_id, sub_to_sub_cat_id, brand, listing_type, listing_price, country, state, city, pincode, address, title, description, item_condition, negotiation_type, open_to_deliver, km_range, publish_datetime, expiry_datetime, user_id, photos_array, rent_type_array,longitude, latitude, barter_text, barter_exchange_text, barter_product_title, barter_product_desc, barter_additional_text, rent_product_detail, rent_terms_and_condition)
    }



}