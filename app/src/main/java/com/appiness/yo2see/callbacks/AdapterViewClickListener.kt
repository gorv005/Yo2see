package com.appiness.yo2see.callbacks

interface AdapterViewClickListener<T> {

    fun onClickAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}