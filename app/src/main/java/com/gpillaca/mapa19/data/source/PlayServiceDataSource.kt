package com.gpillaca.mapa19.data.source

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.gpillaca.mapa19.data.source.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServiceDataSource(private val context: Context) :
    LocationDataSource {

    private val fusedLocation = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun findLocation(): Location = suspendCancellableCoroutine { continuation ->
        fusedLocation.lastLocation
            .addOnSuccessListener {
                continuation.resume(it)
        }
    }
}