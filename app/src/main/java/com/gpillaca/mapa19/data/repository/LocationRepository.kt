package com.gpillaca.mapa19.data.repository

import android.location.Location

interface LocationRepository {
    suspend fun myPosition(): Location
}