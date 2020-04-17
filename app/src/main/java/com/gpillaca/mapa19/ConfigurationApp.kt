package com.gpillaca.mapa19

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.gpillaca.mapa19.di.appModule
import com.gpillaca.mapa19.util.CrashlyticsTree
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

class ConfigurationApp: Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ConfigurationApp))
        import(appModule(applicationContext))
    }

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