package com.gsa.ui.login


import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.list_dropdown.ListOfDropDownResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList
import com.dartmic.yo2see.network.AppRestApiFast

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import java.util.ArrayList

class AddProdcutRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : AddProductRepository {


    override fun uploadImages(
        service: RequestBody,
        user_id: RequestBody,
        type: RequestBody,
        images: MultipartBody.Part
    ): Single<UploadImageResponse> {
        return restApi.uploadImage(service, user_id, type, images)
    }

    override fun getUser(service: String, user_id: String): Single<LoginResponsePayload> {
        return restApi.getUserList(service, user_id)
    }

    override fun getType(service: String, gen_type: String): Single<ListOfDropDownResponse> {
        return restApi.getTypelist(service, gen_type)
    }

    override fun addProduct(
        service: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        brand: String,
        isRent: String,
        isBarter: String,
        isSell: String,
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
        return restApi.addProdcut(
            service,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            brand,
            isRent,
            isBarter,
            isSell,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            item_condition,
            negotiation_type,
            open_to_deliver,
            km_range,
            publish_datetime,
            expiry_datetime,
            user_id,
            photos_array,
            rent_type_array,
            longitude,
            latitude,
            barter_text,
            barter_exchange_text,
            barter_product_title,
            barter_product_desc,
            barter_additional_text,
            rent_product_detail,
            rent_terms_and_condition
        )
    }

    override fun addJob(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        open_to_deliver: String,
        km_range: String,
        publish_datetime: String,
        longitude: String,
        latitude: String,
        job_skills: String,
        job_level: String,
        job_type: String,
        job_responsibility: String
    ): Single<AddProdcutResponse> {
        return restApi.addJob(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            open_to_deliver,
            km_range,
            publish_datetime,
            longitude,
            latitude,
            job_skills,
            job_level,
            job_type,
            job_responsibility
        )
    }

    override fun addEvent(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        event_from: String,
        event_to: String,
        longitude: String,
        latitude: String,
        event_type: String
    ): Single<AddProdcutResponse> {
        return restApi.addEvent(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            event_from,
            event_to,
            longitude,
            latitude,
            event_type
        )
    }

    override fun addBusiness(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray
    ): Single<AddProdcutResponse> {
        return restApi.addBusiness(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            business_type,
            image_type_array
        )

    }

    override fun addBlog(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray
    ): Single<AddProdcutResponse> {
        return restApi.addBlog(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            business_type,
            image_type_array
        )

    }

    override fun addPoem(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addPoem(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )
    }

    override fun addStory(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addStory(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )
    }

    override fun addFreelance(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addFreelance(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )

    }

    override fun addLocalService(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addLocalService(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )

    }
    override fun addBusinessForSale(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray
    ): Single<AddProdcutResponse> {
        return restApi.addBusinessForSale(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            business_type,
            image_type_array
        )

    }
    override fun addUncategorized(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addUncategorized(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )

    }
    override fun addVolunteering(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        type: String
    ): Single<AddProdcutResponse> {
        return restApi.addVolunteering(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            type
        )

    }

    override fun addForum(
        service: String,
        user_id: String,
        category_id: String,
        sub_cat_id: String,
        sub_to_sub_cat_id: String,
        listing_price: String,
        country: String,
        state: String,
        city: String,
        pincode: String,
        address: String,
        title: String,
        description: String,
        publish_datetime: String,
        expiry_datetime: String,
        longitude: String,
        latitude: String,
        business_type: String,
        image_type_array: JSONArray
    ): Single<AddProdcutResponse> {
        return restApi.addForum(
            service,
            user_id,
            category_id,
            sub_cat_id,
            sub_to_sub_cat_id,
            listing_price,
            country,
            state,
            city,
            pincode,
            address,
            title,
            description,
            publish_datetime,
            expiry_datetime,
            longitude,
            latitude,
            business_type,
            image_type_array
        )

    }

}