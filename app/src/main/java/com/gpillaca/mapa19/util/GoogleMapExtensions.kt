package com.gpillaca.mapa19.util

import com.google.android.gms.maps.GoogleMap

fun GoogleMap.defaultConfig() = apply {
    uiSettings.isMyLocationButtonEnabled = false
    uiSettings.isZoomControlsEnabled = false
    uiSettings.isCompassEnabled = false
    isMyLocationEnabled = true
    uiSettings.isMapToolbarEnabled = false
}