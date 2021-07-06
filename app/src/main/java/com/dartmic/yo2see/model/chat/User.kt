package com.dartmic.yo2see.model.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by ansh on 25/08/18.
 */
@Parcelize
data class User(
        val uid: String,
        val name: String,
        val profileImageUrl: String?
) : Parcelable {
    constructor() : this("", "", "")
}