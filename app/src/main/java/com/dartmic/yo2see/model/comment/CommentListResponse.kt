package com.dartmic.yo2see.model.comment


import com.google.gson.annotations.SerializedName

data class ListingItemComment(
    @SerializedName("UserName")
    val userName: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("CreatedDate")
    val createdDate: String = "",
    @SerializedName("comment")
    var comment: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("isOpen")
    val isOpen: Boolean = false,
    @SerializedName("UserEmail")
    val userEmail: String = "",
    @SerializedName("ChildArray")
    val childArray: List<ChildArrayItem>?
)


data class ChildArrayItem(
    @SerializedName("UserName")
    val userName: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("parent_id")
    val parentId: String = "",
    @SerializedName("CreatedDate")
    val createdDate: String = "",
    @SerializedName("comment")
    val comment: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("UserEmail")
    val userEmail: String = ""
)


data class CommentListResponse(
    @SerializedName("Listing")
    val listing: ArrayList<ListingItemComment>?,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
)


