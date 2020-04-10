package com.gpillaca.mapa19.splash

import android.location.Location

interface LocationDataSource {
    suspend fun findLocation(): Location
}