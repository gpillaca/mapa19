package com.gpillaca.mapa19.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionHelperImpl : PermissionHelper {

    private val accessFineLocation: String by lazy {
        android.Manifest.permission.ACCESS_FINE_LOCATION
    }
    private val accessCoarseLocation: String by lazy {
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    }

    override fun requestLocationPermission(requestCode: Int, fragment: Fragment) {
        if (Build.VERSION.SDK_INT <= 22) {
            fragment.requestPermissions(arrayOf(accessCoarseLocation, accessFineLocation),
                requestCode
            )
        } else {
            fragment.context?.let {
                if (locationPermissionNotGranted(it)) {
                    fragment.requestPermissions(
                        arrayOf(accessCoarseLocation, accessFineLocation),
                        requestCode
                    )
                }
            }
        }
    }

    override fun locationPermissionNotGranted(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(context, accessFineLocation) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, accessCoarseLocation) != PackageManager.PERMISSION_GRANTED
    }
}