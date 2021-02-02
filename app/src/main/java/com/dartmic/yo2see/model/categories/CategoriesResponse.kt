package com.dartmic.yo2see.model.categories

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(@SerializedName("CategoryList")
                              val categoryList: List<CategoryListItem>?,
                              @SerializedName("message")
                              val message: String = "",
                              @SerializedName("status")
                              val status: Boolean = false)