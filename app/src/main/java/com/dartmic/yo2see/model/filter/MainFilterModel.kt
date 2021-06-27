package com.dartmic.yo2see.model.filter

import kotlin.collections.ArrayList

class MainFilterModel {


    var title: String? = null
    internal var sub: String? = null
    var isSelected: Boolean = false
    var subtitles = ArrayList<String?>()
    var id = ArrayList<Int?>()

    fun setSub(sub: String) {
        this.sub = sub
    }

     object Model{
         const val CATEGORY = 1
         const val BRAND = 2
         const val PRICE = 3

         const val INDEX_CATEGORY = 0
         const val INDEX_BRAND = 1
         const val INDEX_PRICE = 2
    }
}
