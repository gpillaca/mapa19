package com.gpillaca.mapa19

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gpillaca.mapa19.common.util.CrashlyticsTree
import timber.log.Timber

class ConfigurationApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimberWithFirebaseCrashlytics()
    }

    private fun initTimberWithFirebaseCrashlytics() {
        if (BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            Timber.plant(Timber.DebugTree())
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Timber.plant(CrashlyticsTree())
        }
    }
}