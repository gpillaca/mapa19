package com.gpillaca.mapa19.util

import android.content.Context
import androidx.fragment.app.Fragment

interface PermissionHelper {
    fun requestLocationPermission(requestCode: Int, fragment: Fragment)
    fun locationPermissionNotGranted(context: Context): Boolean
}