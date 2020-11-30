package com.dartmic.yo2see.model.categories

import com.google.gson.annotations.SerializedName

data class FeatureListResponse(@SerializedName("FeatureProductList")
                               val featureProductList: ArrayList<FeatureProductListItem>?,
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("status")
                               val status: Boolean = false)