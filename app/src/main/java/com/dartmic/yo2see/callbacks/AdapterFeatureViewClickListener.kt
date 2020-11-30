package com.dartmic.yo2see.callbacks

interface AdapterFeatureViewClickListener<T> {

    fun onClickFeatureAdapterView(objectAtPosition: T, viewType: Int, position: Int)
}