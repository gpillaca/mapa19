package com.gpillaca.mapa19.splash

import android.location.Location

interface LocationRepository {
    suspend fun myPosition(): Location
}