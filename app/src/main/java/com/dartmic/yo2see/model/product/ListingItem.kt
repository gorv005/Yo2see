package com.dartmic.yo2see.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListingItem(@SerializedName("listing_description")
                       val listingDescription: String = "",
                       @SerializedName("listing_cover_image")
                       val listingCoverImage: String = "",
                       @SerializedName("listing_city")
                       val listingCity: String = "",
                       @SerializedName("listing_image")
                       val listingImage: String = "",
                       @SerializedName("listing_state")
                       val listingState: String = "",
                       @SerializedName("listing_country")
                       val listingCountry: String = "",
                       @SerializedName("listing_address")
                       val listingAddress: String = "",
                       @SerializedName("CategoryName")
                       val categoryName: String = "",
                       @SerializedName("listing_price")
                       val listingPrice: String = "",
                       @SerializedName("id")
                       val id: String = "",
                       @SerializedName("listing_pincode")
                       val listingPincode: String = "",
                       @SerializedName("listing_expiry_datetime")
                       val listingExpiryDatetime: String = "",
                       @SerializedName("listing_publish_datetime")
                       val listingPublishDatetime: String = ""): Parcelable