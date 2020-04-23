package com.gpillaca.mapa19.di

import com.gpillaca.mapa19.ui.splash.SplashPresenter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

fun splashModule() = Kodein.Module("splashModule") {
    bind<SplashPresenter>() with provider { SplashPresenter() }
}