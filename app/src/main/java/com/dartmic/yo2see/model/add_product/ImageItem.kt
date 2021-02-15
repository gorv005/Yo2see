package com.dartmic.yo2see.model.add_product

import android.graphics.Bitmap
import android.net.Uri
import com.google.gson.annotations.SerializedName

data class ImageItem(
    @SerializedName("avatar_url")
    val fileUrl: Uri?,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("thumb_url")
    val serverurl: String?=""

)