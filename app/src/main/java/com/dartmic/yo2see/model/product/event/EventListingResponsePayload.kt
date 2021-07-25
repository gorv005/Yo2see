package com.dartmic.yo2see.model.product.event


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class EventListingResponsePayload(@SerializedName("Listing")
                                       val listing: ArrayList<ListingItemEvent>?,
                                       @SerializedName("message")
                                       val message: String?,
                                       @SerializedName("status")
                                       val status: Boolean = false)

@Parcelize
data class ListingItemEvent(@SerializedName("job_type")
                       val jobType: String?,
                       @SerializedName("UserMobile")
                       val userMobile: String?,
                       @SerializedName("UserFavorite")
                       var userFavorite: Int?,
                       @SerializedName("UserLatitude")
                       val userLatitude: String?,
                       @SerializedName("e_city")
                       val eCity: String?,
                       @SerializedName("latitude")
                       val latitude: String?,
                       @SerializedName("open_to_deliver")
                       val openToDeliver: String?,
                       @SerializedName("event_from")
                       val eventFrom: String?,
                       @SerializedName("e_state")
                       val eState: String?,
                       @SerializedName("UserEmail")
                       val userEmail: String?,
                       @SerializedName("job_skills")
                       val jobSkills: String?,
                       @SerializedName("event_type")
                       val eventType: String?,
                       @SerializedName("event_to")
                       val eventTo: String?,
                       @SerializedName("category_id")
                       val categoryId: String?,
                       @SerializedName("km_range")
                       val kmRange: String?,
                       @SerializedName("UserUID")
                       val userUID: String?,
                       @SerializedName("e_expiry_datetime")
                       val eExpiryDatetime: String?,
                       @SerializedName("e_publish_datetime")
                       val ePublishDatetime: String?,
                       @SerializedName("e_pincode")
                       val ePincode: String?,
                       @SerializedName("e_country")
                       val eCountry: String?,
                       @SerializedName("id")
                       val id: String?,
                       @SerializedName("job_level")
                       val jobLevel: String?,
                       @SerializedName("subtosub_cat_id")
                       val subtosubCatId: String?,
                       @SerializedName("longitude")
                       val longitude: String?,
                       @SerializedName("UserName")
                       val userName: String?,
                       @SerializedName("sub_cat_id")
                       val subCatId: String?,
                       @SerializedName("UsersLongitude")
                       val usersLongitude: String?,
                       @SerializedName("ntype")
                       val ntype: String?,
                       @SerializedName("SubCategoryName")
                       val subCategoryName: String?,
                       @SerializedName("e_description")
                       val eDescription: String?,
                       @SerializedName("user_id")
                       val userId: String?,
                       @SerializedName("CategoryName")
                       val categoryName: String?,
                       @SerializedName("UsersPhoto")
                       val usersPhoto: String?,
                       @SerializedName("e_title")
                       val eTitle: String?,
                       @SerializedName("created_date")
                       val createdDate: String?,
                       @SerializedName("e_address")
                       val eAddress: String?,
                       @SerializedName("job_responsibility")
                       val jobResponsibility: String?,
                       @SerializedName("e_price")
                       val ePrice: String?): Parcelable


