package com.dartmic.yo2see.model.product


import com.google.gson.annotations.SerializedName

data class RentTypeListItem(
    @SerializedName("rent_type")
    val rentType: String = "",
    @SerializedName("listing_id")
    val listingId: String = "",
    @SerializedName("payment")
    val payment: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("created_date")
    val createdDate: String = ""
)


data class ProductDetailResponsePayload(
    @SerializedName("rent_type_list")
    val rentTypeList: List<RentTypeListItem>?,
    @SerializedName("LoginUserDetails")
    val loginUserDetails: List<LoginUserDetailsItem>?,
    @SerializedName("detail")
    val detail: List<DetailItem>?,
    @SerializedName("gallery_list")
    val galleryList: List<GalleryListItem>?,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: String = ""
)


data class DetailItem(
    @SerializedName("UserMobile")
    val userMobile: String = "",
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
    @SerializedName("listing_type")
    val listingType: String = "",
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
    @SerializedName("listing_publish_datetime")
    val listingPublishDatetime: String = ""
)


data class GalleryListItem(
    @SerializedName("image_name")
    val imageName: String = "",
    @SerializedName("list_id")
    val listId: String = "",
    @SerializedName("id")
    val id: String = ""
)


data class LoginUserDetailsItem(
    @SerializedName("device_id")
    val deviceId: String = "",
    @SerializedName("user_activation_date")
    val userActivationDate: String?,
    @SerializedName("user_name")
    val userName: String = "",
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("device_type")
    val deviceType: String?,
    @SerializedName("modified_date")
    val modifiedDate: String = "",
    @SerializedName("given_name")
    val givenName: String?,
    @SerializedName("locale")
    val locale: String?,
    @SerializedName("userID")
    val userID: String = "",
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("login_from")
    val loginFrom: String?,
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("created_date")
    val createdDate: String = "",
    @SerializedName("family_name")
    val familyName: String?,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("longitude")
    val longitude: String?
)


