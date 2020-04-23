package com.gpillaca.mapa19.data.repository

import android.location.Location
import com.gpillaca.mapa19.data.source.DataBaseDataSource
import com.gpillaca.mapa19.data.source.LocationDataSource

class LocationRepositoryImpl(
    private val dataBaseDataSource: DataBaseDataSource,
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    override suspend fun myPosition(): Location {
        return locationDataSource.findLocation()
    }
}