package com.appiness.yo2see.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.appiness.yo2see.BuildConfig
import com.appiness.yo2see.common.HeaderInterceptor
import com.appiness.yo2see.network.AppRestApiFast
import com.appiness.yo2see.network.AuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val EXTENDED_TIMEOUT = "EXTENDED_TIMEOUT"
const val NORMAL_TIMEOUT = "NORMAL_TIMEOUT"

object NetworkModule {

        val networkModule = module {

            single { provideDefaultOkhttpClient() }
            single { provideRetrofit(get())}
            single() { provideTmdbService(get()) }


        }

        fun provideDefaultOkhttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val h = HeaderInterceptor()

            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(h)
                .callTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(3000, TimeUnit.SECONDS)

                .build()
        }

    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.disableHtmlEscaping()

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")
        builder.setDateFormat(format.toLocalizedPattern())

        return builder.create()
    }

        fun provideRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

    fun provideTmdbService(retrofit: Retrofit): AppRestApiFast = retrofit.create(AppRestApiFast::class.java)
}