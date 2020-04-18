package com.gpillaca.mapa19.domain

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VulnerablePerson(
    val id: String,
    val positionLatLng: LatLng,
    val name: String,
    val dni: String,
    val phoneNumber: String,
    val message: String,
    val type: Int,
    val estHelp: Int, // estHelp??
    val nHelp: Int, //nhelp??
    val noRep: Int //noRep??
) : Parcelable