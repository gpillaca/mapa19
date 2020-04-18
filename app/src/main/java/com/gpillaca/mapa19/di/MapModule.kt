package com.gpillaca.mapa19.di

import com.gpillaca.mapa19.data.repository.MapRepository
import com.gpillaca.mapa19.data.repository.MapRepositoryImpl
import com.gpillaca.mapa19.ui.map.MapPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun mapModule() = Kodein.Module("mapModule") {
    bind<MapRepository>() with singleton {
        MapRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance()
        )
    }

    bind<MapPresenter>() with provider {
        MapPresenter(
            mapRepository = instance(),
            locationRepository = instance()
        )
    }
}