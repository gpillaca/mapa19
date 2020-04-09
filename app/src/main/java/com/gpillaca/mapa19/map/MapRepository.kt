package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.server.MapDbResult

interface MapRepository {
    suspend fun listVulnerablePersons(): MapDbResult
}