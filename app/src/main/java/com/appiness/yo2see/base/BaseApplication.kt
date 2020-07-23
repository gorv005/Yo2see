package com.appiness.yo2see.base

import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco



class BaseApplication : MultiDexApplication() {



    override fun onCreate() {
        super.onCreate()
        sInstance = this


        Fresco.initialize(this)

    }
    companion object {

        private lateinit var sInstance: BaseApplication

        fun getInstance() = sInstance
    }
}