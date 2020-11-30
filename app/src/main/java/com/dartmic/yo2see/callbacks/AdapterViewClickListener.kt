package com.dartmic.yo2see.callbacks

interface AdapterViewClickListener<T> {

    fun onClickAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}