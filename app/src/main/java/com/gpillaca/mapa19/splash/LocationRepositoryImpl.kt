package com.gpillaca.mapa19.splash

import android.location.Location
import com.gpillaca.mapa19.data.LocalDataSource

class LocationRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun myPosition(): Location {
        return locationDataSource.findLocation()
    }
}