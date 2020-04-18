package com.gpillaca.mapa19.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VulnerablePerson(
    @PrimaryKey(autoGenerate = false) val id: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val dni: String,
    val phoneNumber: String,
    val message: String,
    val type: Int,
    val estHelp: Int, // estHelp??
    val nHelp: Int, //nhelp??
    val noRep: Int
)