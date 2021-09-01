package com.dartmic.yo2see.model.my_history


import com.google.gson.annotations.SerializedName

data class MyListingItem(@SerializedName("SubCategoryName")
                         val subCategoryName: String?,
                         @SerializedName("TableName")
                         val tableName: String?,
                         @SerializedName("Description")
                         val description: String?,
                         @SerializedName("Price")
                         val price: String?,
                         @SerializedName("Table_Id")
                         val tableId: String?,
                         @SerializedName("CategoryName")
                         val categoryName: String?,
                         @SerializedName("Title")
                         val title: String?,
                         @SerializedName("SubToSubCategoryName")
                         val subToSubCategoryName: String?,
                         @SerializedName("ItemRating")
                         val itemRating: String?,
                         @SerializedName("nType")
                         val nType: String?)


data class PostHistoryResponsePayload(@SerializedName("message")
                                      val message: String?,
                                      @SerializedName("MyListing")
                                      val myListing: ArrayList<MyListingItem>?,
                                      @SerializedName("status")
                                      val status: Boolean = false)


