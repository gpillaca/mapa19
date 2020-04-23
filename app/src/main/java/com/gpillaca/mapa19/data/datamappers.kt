package com.gpillaca.mapa19.data

import com.google.android.gms.maps.model.LatLng
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.data.database.VulnerablePerson as DataBaseVulnerablePerson
import com.gpillaca.mapa19.util.toJsonString
import com.gpillaca.mapa19.ui.map.cluster.PersonItem
import com.gpillaca.mapa19.domain.VulnerablePerson as DomainVulnerablePerson

fun DomainVulnerablePerson.convertToPersonItems() = PersonItem(
    this.positionLatLng,
    this.toJsonString(),
    "",
    getMarker(this.estHelp)
)

fun DomainVulnerablePerson.toDataBaseVulnerablePersons() = DataBaseVulnerablePerson(
    id,
    positionLatLng.latitude,
    positionLatLng.longitude,
    name,
    dni,
    phoneNumber,
    message,
    type,
    estHelp,
    nHelp,
    noRep
)

fun DataBaseVulnerablePerson.toDomainVulnerablePersons() = DomainVulnerablePerson(
    id,
    LatLng(latitude, longitude),
    name,
    dni,
    phoneNumber,
    message,
    type,
    estHelp,
    nHelp,
    noRep
)

private fun getMarker(type: Int): Int {
    return if (type == 2) {
        R.drawable.ic_help
    } else {
        R.drawable.ic_helped
    }
}