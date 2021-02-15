package com.dartmic.yo2see.interfaces


internal interface ResultListener<T> {

    fun onResult(t: T?)
}