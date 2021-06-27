package com.dartmic.yo2see.model.filter

import android.os.Parcel
import android.os.Parcelable


class FilterDefaultMultipleListModel() : Parcelable {
    var name: String?=""
    var id: Int?=-1
    var isChecked = false

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        isChecked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(id)
        parcel.writeByte(if (isChecked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilterDefaultMultipleListModel> {
        override fun createFromParcel(parcel: Parcel): FilterDefaultMultipleListModel {
            return FilterDefaultMultipleListModel(parcel)
        }

        override fun newArray(size: Int): Array<FilterDefaultMultipleListModel?> {
            return arrayOfNulls(size)
        }
    }
}
