package com.dartmic.yo2see.model.login


import com.google.gson.annotations.SerializedName

data class LoginResponsePayload(@SerializedName("user_list")
                                var userList: UserList?,
                                @SerializedName("message")
                                var message: String = "",
                                @SerializedName("status")
                                var status: Boolean = false)


data class UserList(@SerializedName("user_id")
                    val userId: String = "",
                    @SerializedName("phone")
                    val phone: String = "",
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("created_date")
                    val createdDate: String = "",
                    @SerializedName("is_user_login")
                    val isUserLogin: Boolean = false,
                    @SerializedName("email")
                    val email: String = "",
                    @SerializedName("login_from")
                    val loginFrom: String?,
                    @SerializedName("status")
                    val status: String = "")


