package com.gpillaca.mapa19.common

import com.gpillaca.mapa19.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun httpClient(loggingInterceptor: Interceptor, responseInterceptor: ResponseInterceptor): OkHttpClient =
    OkHttpClient.Builder().addInterceptor(responseInterceptor).also {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(loggingInterceptor)
        }
    }.build()