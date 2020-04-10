package com.gpillaca.mapa19.map

interface MapRepository {
    suspend fun listVulnerablePersons(): List<VulnerablePerson>
}