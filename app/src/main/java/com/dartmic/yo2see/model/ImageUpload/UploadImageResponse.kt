package com.dartmic.yo2see.model.ImageUpload

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(@SerializedName("Path")
                               val path: String = "",
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("status")
                               val status: Boolean = false)