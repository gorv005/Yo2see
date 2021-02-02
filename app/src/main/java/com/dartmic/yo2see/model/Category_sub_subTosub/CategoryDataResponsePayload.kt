package com.dartmic.yo2see.model.Category_sub_subTosub


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SubCatListItem(@SerializedName("category_id")
                          val categoryId: String = "",
                          @SerializedName("SubToSubList")
                          val subToSubList: List<SubToSubListItem>?,
                          @SerializedName("sub_category_id")
                          val subCategoryId: String = "",
                          @SerializedName("sub_category_name")
                          val subCategoryName: String = "",
                          @SerializedName("CategoryName")
                          val categoryName: String = ""): Parcelable

@Parcelize
data class SubToSubListItem(@SerializedName("SubCategoryName")
                            val subCategoryName: String = "",
                            @SerializedName("sub_subcategory_name")
                            val subSubcategoryName: String = "",
                            @SerializedName("category_id")
                            val categoryId: String = "",
                            @SerializedName("sub_category_id")
                            val subCategoryId: String = "",
                            @SerializedName("CategoryName")
                            val categoryName: String = "",
                            @SerializedName("id")
                            val id: String = ""): Parcelable

@Parcelize
data class CategoryListItemData(@SerializedName("cat_image")
                            val catImage: String = "",
                            @SerializedName("category_name")
                            val categoryName: String = "",
                            @SerializedName("SubCatList")
                            val subCatList: List<SubCatListItem>?,
                            @SerializedName("category_id")
                            val categoryId: String = ""): Parcelable

@Parcelize
data class CategoryDataResponsePayload(@SerializedName("CategoryList")
                                       val categoryList: List<CategoryListItemData>?,
                                       @SerializedName("message")
                                       val message: String = "",
                                       @SerializedName("status")
                                       val status: Boolean = false): Parcelable


