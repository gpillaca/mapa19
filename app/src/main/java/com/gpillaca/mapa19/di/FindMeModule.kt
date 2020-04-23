package com.gpillaca.mapa19.di

import com.gpillaca.mapa19.ui.findme.FindMePresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

fun finMeModule() = Kodein.Module("findMeModule") {
    bind<FindMePresenter>() with provider {
        FindMePresenter()
    }
}