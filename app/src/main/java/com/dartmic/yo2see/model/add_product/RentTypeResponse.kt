package com.dartmic.yo2see.model.add_product

import com.google.gson.annotations.SerializedName

data class RentTypeResponse(@SerializedName("rent_type")
                            val rentType: String = "",
                            @SerializedName("payment")
                            val payment: String = "")