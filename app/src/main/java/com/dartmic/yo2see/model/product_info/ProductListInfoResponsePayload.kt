package com.dartmic.yo2see.model.product_info


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ProductListInfoResponsePayload(@SerializedName("Listing")
                                          val listing: ArrayList<ListingItem>?,
                                          @SerializedName("message")
                                          val message: String = "",
                                          @SerializedName("status")
                                          val status: Boolean = false)

@Parcelize
data class ListingItem(@SerializedName("UserMobile")
                       val userMobile: String = "",
                       @SerializedName("UserFavorite")
                       var userFavorite: Int?,
                       @SerializedName("listing_description")
                       val listingDescription: String = "",
                       @SerializedName("UserLatitude")
                       val userLatitude: String = "",
                       @SerializedName("rent_terms_and_condition")
                       val rentTermsAndCondition: String = "",
                       @SerializedName("latitude")
                       val latitude: String = "",
                       @SerializedName("open_to_deliver")
                       val openToDeliver: String = "",
                       @SerializedName("UserEmail")
                       val userEmail: String = "",
                       @SerializedName("barter_additional_text")
                       val barterAdditionalText: String = "",
                       @SerializedName("listing_cover_image")
                       val listingCoverImage: String = "",
                       @SerializedName("listing_title")
                       val listingTitle: String = "",
                       @SerializedName("listing_image")
                       val listingImage: String = "",
                       @SerializedName("is_barter")
                       val isBarter: String = "",
                       @SerializedName("category_id")
                       val categoryId: String = "",
                       @SerializedName("listing_state")
                       val listingState: String = "",
                       @SerializedName("km_range")
                       val kmRange: String = "",
                       @SerializedName("sub_category_id")
                       val subCategoryId: String = "",
                       @SerializedName("listing_address")
                       val listingAddress: String = "",
                       @SerializedName("item_condition")
                       val itemCondition: String = "",
                       @SerializedName("listing_price")
                       val listingPrice: String = "",
                       @SerializedName("negotiation_type")
                       val negotiationType: String = "",
                       @SerializedName("id")
                       val id: String = "",
                       @SerializedName("is_sell")
                       val isSell: String = "",
                       @SerializedName("brand")
                       val brand: String = "",
                       @SerializedName("longitude")
                       val longitude: String = "",
                       @SerializedName("barter_product_title")
                       val barterProductTitle: String = "",
                       @SerializedName("UserName")
                       val userName: String = "",
                       @SerializedName("barter_exchange_text")
                       val barterExchangeText: String = "",
                       @SerializedName("sub_to_sub_cat_id")
                       val subToSubCatId: String = "",
                       @SerializedName("UsersLongitude")
                       val usersLongitude: String = "",
                       @SerializedName("barter_text")
                       val barterText: String = "",
                       @SerializedName("SubCategoryName")
                       val subCategoryName: String = "",
                       @SerializedName("rent_product_detail")
                       val rentProductDetail: String = "",
                       @SerializedName("barter_product_desc")
                       val barterProductDesc: String = "",
                       @SerializedName("listing_city")
                       val listingCity: String = "",
                       @SerializedName("user_id")
                       val userId: String = "",
                       @SerializedName("listing_country")
                       val listingCountry: String = "",
                       @SerializedName("is_rent")
                       val isRent: String = "",
                       @SerializedName("CategoryName")
                       val categoryName: String = "",
                       @SerializedName("UsersPhoto")
                       val usersPhoto: String = "",
                       @SerializedName("listing_pincode")
                       val listingPincode: String = "",
                       @SerializedName("listing_expiry_datetime")
                       val listingExpiryDatetime: String = "",
                       @SerializedName("ItemRating")
                       val itemRating: String = "",
                       @SerializedName("UserUID")
                       val userUID: String = "",
                       @SerializedName("AvgRatingUser")
                       val AvgRatingUser: String = "",
                       @SerializedName("listing_publish_datetime")
                       val listingPublishDatetime: String = "",
                       @SerializedName("RentType")
                       val rentType: List<RentTypeItem>?): Parcelable

@Parcelize
data class RentTypeItem(@SerializedName("rent_type")
                        val rentType: String = "",
                        @SerializedName("listing_id")
                        val listingId: String = "",
                        @SerializedName("payment")
                        val payment: String = ""): Parcelable


