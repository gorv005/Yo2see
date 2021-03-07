package com.dartmic.yo2see.model.signUp

import com.google.gson.annotations.SerializedName

data class OTPResponsePayload(@SerializedName("otp")
                              val otp: Int = 0,
                              @SerializedName("message")
                              val message: String = "",
                              @SerializedName("status")
                              val status: Boolean = false)