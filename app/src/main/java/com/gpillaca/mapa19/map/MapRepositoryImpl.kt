package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.server.MapDbResult
import com.gpillaca.mapa19.server.MapDbService
import retrofit2.Retrofit

class MapRepositoryImpl(private val retrofit: Retrofit): MapRepository {

    override suspend fun listVulnerablePersons(): MapDbResult {
        return retrofit.create(MapDbService::class.java).listVulnerablePersons()
    }
}