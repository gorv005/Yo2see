package com.appiness.yo2see.base

import android.os.Bundle
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.appiness.yo2see.di.AppModule
import com.appiness.yo2see.di.NetworkModule
import com.appiness.yo2see.managers.ActivityLifecycleManager
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin


class BaseApplication : MultiDexApplication() {



    override fun onCreate() {
        super.onCreate()
        sInstance = this

        startKoin {
            androidContext(this@BaseApplication)
            logger(AndroidLogger())
            modules(listOf(AppModule.appModule, NetworkModule.networkModule))
        }
        Fresco.initialize(this)

    }
    companion object {

        private lateinit var sInstance: BaseApplication

        fun getInstance() = sInstance
    }
}