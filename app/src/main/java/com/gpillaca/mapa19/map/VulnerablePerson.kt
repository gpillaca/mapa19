package com.gpillaca.mapa19.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VulnerablePerson(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val dni: String,
    val phoneNumber: String,
    val message: String,
    val type: Int,
    val estHelp: Int, // estHelp??
    val nHelp: Int, //nhelp??
    val noRep: Int //noRep??
) : Parcelable