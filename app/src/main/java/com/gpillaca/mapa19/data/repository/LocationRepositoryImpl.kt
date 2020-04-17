package com.gpillaca.mapa19.data.repository

import android.location.Location
import com.gpillaca.mapa19.data.source.LocalDataSource
import com.gpillaca.mapa19.data.source.LocationDataSource

class LocationRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun myPosition(): Location {
        return locationDataSource.findLocation()
    }
}