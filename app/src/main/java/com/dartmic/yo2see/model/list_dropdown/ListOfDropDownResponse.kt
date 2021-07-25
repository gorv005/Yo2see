package com.dartmic.yo2see.model.list_dropdown


import com.google.gson.annotations.SerializedName

data class ListOfDropDownResponse(@SerializedName("message")
                                  val message: String = "",
                                  @SerializedName("GeneralList")
                                  val generalList: ArrayList<GeneralListItem>?,
                                  @SerializedName("status")
                                  val status: Boolean = false)


data class GeneralListItem(@SerializedName("gen_value")
                           val genValue: String = "",
                           @SerializedName("gen_text")
                           val genText: String = "",
                           @SerializedName("gen_type")
                           val genType: String = "")


