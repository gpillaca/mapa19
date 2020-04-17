package com.gpillaca.mapa19.util

import android.content.Context
import android.net.ConnectivityManager

class AndroidHelper (private val context: Context) {

    fun isNetWorkActive(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.isDefaultNetworkActive
    }

    fun getString(value: Int): String {
        return context.getString(value)
    }
}