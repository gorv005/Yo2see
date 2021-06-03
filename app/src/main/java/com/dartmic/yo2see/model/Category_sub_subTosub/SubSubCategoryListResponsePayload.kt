package com.dartmic.yo2see.model.Category_sub_subTosub


import com.google.gson.annotations.SerializedName

data class SubCategoryListItem(@SerializedName("category_id")
                               val categoryId: String = "",
                               @SerializedName("sub_category_id")
                               val subCategoryId: String = "",
                               @SerializedName("sub_category_name")
                               val subCategoryName: String = "",
                               @SerializedName("CategoryName")
                               val categoryName: String = "")


data class SubSubCategoryListResponsePayload(@SerializedName("SubCategoryList")
                                             val subCategoryList: List<SubCategoryListItem>?,
                                             @SerializedName("message")
                                             val message: String = "",
                                             @SerializedName("status")
                                             val status: Boolean = false)


