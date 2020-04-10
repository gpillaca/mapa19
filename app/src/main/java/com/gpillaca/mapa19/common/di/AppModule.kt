package com.gpillaca.mapa19.common.di

import android.content.Context
import com.gpillaca.mapa19.common.AppSharedPreferences
import com.gpillaca.mapa19.common.ResponseInterceptor
import com.gpillaca.mapa19.common.httpClient
import com.gpillaca.mapa19.common.loggingInterceptor
import com.gpillaca.mapa19.common.util.AndroidHelper
import com.gpillaca.mapa19.common.util.PermissionHelper
import com.gpillaca.mapa19.common.util.PermissionHelperImpl
import com.gpillaca.mapa19.data.LocalDataSource
import com.gpillaca.mapa19.data.SharedPreferencesDataSource
import com.gpillaca.mapa19.splash.LocationDataSource
import com.gpillaca.mapa19.splash.LocationRepository
import com.gpillaca.mapa19.splash.PlayServiceDataSource
import com.gpillaca.mapa19.splash.LocationRepositoryImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun appModule(context: Context) = Kodein.Module("appModule") {
    bind<Context>() with provider { context }
    bind<PermissionHelper>() with provider { PermissionHelperImpl() }
    bind<AndroidHelper>() with provider { AndroidHelper(context = instance()) }
    import(retrofitModule())
    import(localDataModule())
    bind<LocationDataSource>() with provider { PlayServiceDataSource(context = instance()) }
    bind<LocationRepository>() with singleton {
        LocationRepositoryImpl(
            localDataSource = instance(tag = "sharedPreferences"),
            locationDataSource = instance()
        )
    }
}

fun retrofitModule() = Kodein.Module("retrofitModule") {
    bind<Interceptor>(tag = "logging") with singleton { loggingInterceptor() }
    bind<ResponseInterceptor>(tag = "data") with singleton { ResponseInterceptor() }
    bind<OkHttpClient>(tag = "client") with singleton {
        httpClient(instance(tag = "logging"), instance(tag = "data"))
    }

    bind<Retrofit>() with singleton {
        Retrofit.Builder()
            .baseUrl("https://demo2974580.mockable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(instance(tag = "client"))
            .build()
    }
}

fun localDataModule() = Kodein.Module("localDataModule") {
    bind<LocalDataSource>(tag = "sharedPreferences") with singleton {
        SharedPreferencesDataSource(
            sharedPreferences = AppSharedPreferences(context = instance())
        )
    }
}