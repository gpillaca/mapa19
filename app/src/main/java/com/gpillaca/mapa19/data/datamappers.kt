package com.gpillaca.mapa19.data

import com.google.android.gms.maps.model.LatLng
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.data.database.VulnerablePerson as DataBaseVulnerablePerson
import com.gpillaca.mapa19.util.toJsonString
import com.gpillaca.mapa19.ui.map.PersonItem
import com.gpillaca.mapa19.domain.VulnerablePerson as DomainVulnerablePerson

fun List<DomainVulnerablePerson>.convertToPersonItems(): List<PersonItem> {
    val personItems = mutableListOf<PersonItem>()

    this.map { person ->
        personItems.add(
            PersonItem(
                person.positionLatLng,
                person.toJsonString(),
                "",
                getMarker(person.estHelp)
            )
        )
    }

    return personItems
}

fun List<DomainVulnerablePerson>.toDataBaseVulnerablePersons(): List<DataBaseVulnerablePerson> {
    val vulnerablePersons = mutableListOf<DataBaseVulnerablePerson>()

    this.map { person ->
        vulnerablePersons.add(
            with(person) {
                DataBaseVulnerablePerson(
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
            }
        )
    }

    return vulnerablePersons
}

fun List<DataBaseVulnerablePerson>.toDomainVulnerablePersons(): List<DomainVulnerablePerson> {
    val vulnerablePersons = mutableListOf<DomainVulnerablePerson>()

    this.map { person ->
        with(person) {
            vulnerablePersons.add(
                DomainVulnerablePerson(
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
            )
        }
    }

    return vulnerablePersons
}

private fun getMarker(type: Int): Int {
    return if (type == 2) {
        R.drawable.ic_help
    } else {
        R.drawable.ic_helped
    }
}