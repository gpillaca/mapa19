package com.gpillaca.mapa19.common

import com.google.gson.Gson

inline fun <reified T> String.fromJsonStringTo(): T {
    return Gson().fromJson(this, T::class.java)
}

fun <T> T.toJsonString(): String {
    return Gson().toJson(this)
}