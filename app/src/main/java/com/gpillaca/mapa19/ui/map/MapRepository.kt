package com.gpillaca.mapa19.ui.map

interface MapRepository {
    suspend fun listVulnerablePersons(): List<PersonItem>
}