package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.VulnerablePerson

interface RemoteDataSource {
    suspend fun listVulnerablePersons(): List<VulnerablePerson>
}