package com.dartmic.yo2see.callbacks

interface AdapterViewItemClickListener<T> {

    fun onClickItemAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}