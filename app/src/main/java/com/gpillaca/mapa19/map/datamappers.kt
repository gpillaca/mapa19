package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.common.toJsonString

fun List<VulnerablePerson>.convertToPersonItems(): List<PersonItem> {
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

private fun getMarker(type: Int): Int {
    return if (type == 2) {
        R.drawable.ic_help
    } else {
        R.drawable.ic_helped
    }
}