package com.gpillaca.mapa19.common

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson

inline fun <reified T> String.fromJsonStringTo(): T {
    return Gson().fromJson(this, T::class.java)
}

fun <T> T.toJsonString(): String {
    return Gson().toJson(this)
}

fun String.isInt(): Boolean {
    return this.toIntOrNull()?.let { true } ?: false
}

fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}