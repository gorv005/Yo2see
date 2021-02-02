package com.dartmic.yo2see.model.product

import com.google.gson.annotations.SerializedName

data class ProductListResponsePayload(@SerializedName("Listing")
                                      val listing: List<ListingItem>?,
                                      @SerializedName("message")
                                      val message: String = "",
                                      @SerializedName("status")
                                      val status: Boolean = false)