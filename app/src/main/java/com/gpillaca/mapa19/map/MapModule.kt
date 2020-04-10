package com.gpillaca.mapa19.map

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

fun mapModule() = Kodein.Module("mapModule") {
    bind<MapRepository>() with singleton {
        MapRepositoryImpl(
            retrofit = instance()
        )
    }

    bind<MapPresenter>() with provider {
        MapPresenter(
            mapRepository = instance(),
            locationRepository = instance())
    }
}