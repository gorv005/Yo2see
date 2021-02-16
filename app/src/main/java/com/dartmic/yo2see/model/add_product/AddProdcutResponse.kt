package com.dartmic.yo2see.model.add_product

import com.google.gson.annotations.SerializedName

data class AddProdcutResponse(
                              @SerializedName("message")
                               val message: String = "",
                              @SerializedName("status")
                               val status: Boolean = false)