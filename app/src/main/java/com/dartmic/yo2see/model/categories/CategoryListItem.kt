package com.dartmic.yo2see.model.categories

import com.google.gson.annotations.SerializedName

data class CategoryListItem(@SerializedName("cat_image")
                            val catImage: String = "",
                            @SerializedName("category_name")
                            val categoryName: String = "",
                            @SerializedName("category_id")
                            val categoryId: String = "")