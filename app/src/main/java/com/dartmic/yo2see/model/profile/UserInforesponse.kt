package com.dartmic.yo2see.model.profile


import android.os.Parcelable
import com.dartmic.yo2see.model.login.UserList
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserInforesponse(@SerializedName("user_list")
                            val userList: UserList,
                            @SerializedName("message")
                            val message: String?,
                            @SerializedName("status")
                            val status: Boolean = false)

@Parcelize
data class UserList(@SerializedName("user_id")
                    val userId: String?,
                    @SerializedName("phone")
                    val phone: String?,
                    @SerializedName("name")
                    val name: String?,
                    @SerializedName("last_name")
                    val lastName: String?,
                    @SerializedName("first_name")
                    val firstName: String?,
                    @SerializedName("email")
                    val email: String?,
                    @SerializedName("picture")
                    val picture: String?,
                    @SerializedName("status")
                    val status: String?,
                    @SerializedName("login_from")
                    val loginFrom: String?,
                    @SerializedName("profile_link")
                    val profileLink: String?,
                    @SerializedName("usertype")
                    val usertype: String?): Parcelable


