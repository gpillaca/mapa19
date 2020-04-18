package com.gpillaca.mapa19.data.source

import android.location.Location

interface LocationDataSource {
    suspend fun findLocation(): Location
}