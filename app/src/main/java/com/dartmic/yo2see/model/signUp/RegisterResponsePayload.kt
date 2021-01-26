package com.dartmic.yo2see.model.signUp


import com.google.gson.annotations.SerializedName

data class RegisterResponsePayload(@SerializedName("message")
                                   val message: String = "",
                                   @SerializedName("status")
                                   val status: Boolean = false)


