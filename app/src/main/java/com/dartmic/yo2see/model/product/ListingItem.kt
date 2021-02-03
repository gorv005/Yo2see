package com.dartmic.yo2see.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListingItem(@SerializedName("UserMobile")
                              val userMobile: String? = "",
                       @SerializedName("listing_description")
                              val listingDescription: String? = "",
                       @SerializedName("UserLatitude")
                              val userLatitude: String? = "",
                       @SerializedName("latitude")
                              val latitude: String?,
                       @SerializedName("open_to_deliver")
                              val openToDeliver: String? = "",
                       @SerializedName("UserEmail")
                              val userEmail: String? = "",
                       @SerializedName("listing_cover_image")
                              val listingCoverImage: String? = "",
                       @SerializedName("listing_title")
                              val listingTitle: String? = "",
                       @SerializedName("listing_image")
                              val listingImage: String? = "",
                       @SerializedName("category_id")
                              val categoryId: String? = "",
                       @SerializedName("listing_state")
                              val listingState: String? = "",
                       @SerializedName("km_range")
                              val kmRange: String? = "",
                       @SerializedName("sub_category_id")
                              val subCategoryId: String? = "",
                       @SerializedName("listing_address")
                              val listingAddress: String? = "",
                       @SerializedName("item_condition")
                              val itemCondition: String? = "",
                       @SerializedName("listing_price")
                              val listingPrice: String? = "",
                       @SerializedName("negotiation_type")
                              val negotiationType: String? = "",
                       @SerializedName("id")
                              val id: String? = "",
                       @SerializedName("brand")
                              val brand: String? = "",
                       @SerializedName("longitude")
                              val longitude: String?,
                       @SerializedName("UserName")
                              val userName: String? = "",
                       @SerializedName("sub_to_sub_cat_id")
                              val subToSubCatId: String? = "",
                       @SerializedName("listing_type")
                              val listingType: String? = "",
                       @SerializedName("UsersLongitude")
                              val usersLongitude: String? = "",
                       @SerializedName("SubCategoryName")
                              val subCategoryName: String? = "",
                       @SerializedName("listing_city")
                              val listingCity: String? = "",
                       @SerializedName("user_id")
                              val userId: String? = "",
                       @SerializedName("listing_country")
                              val listingCountry: String? = "",
                       @SerializedName("CategoryName")
                              val categoryName: String? = "",
                       @SerializedName("UsersPhoto")
                              val usersPhoto: String? = "",
                       @SerializedName("listing_pincode")
                              val listingPincode: String? = "",
                       @SerializedName("listing_expiry_datetime")
                              val listingExpiryDatetime: String? = "",
                       @SerializedName("ItemRating")
                              val itemRating: String? = "",
                       @SerializedName("listing_publish_datetime")
                              val listingPublishDatetime: String? = ""): Parcelable