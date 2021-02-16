package com.gsa.ui.login


import com.dartmic.yo2see.model.ImageUpload.UploadImageResponse
import com.dartmic.yo2see.model.add_product.AddProdcutResponse
import com.dartmic.yo2see.model.add_product.ImagePathResponse
import com.dartmic.yo2see.model.add_product.RentTypeResponse
import com.dartmic.yo2see.model.login.LoginRequest
import com.dartmic.yo2see.model.login.LoginResponsePayload
import com.dartmic.yo2see.model.login.UserList

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.Part

interface AddProductRepository {
    fun uploadImages( service: RequestBody,
                      user_id: RequestBody,
                      type: RequestBody,
                     images: MultipartBody.Part
    ) : Single<UploadImageResponse>
    fun addProduct(service: String,
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
                  photos_array: List<ImagePathResponse>,
                  rent_type_array:  List<RentTypeResponse>

    ) : Single<AddProdcutResponse>

}