package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

interface DataBaseStorage {
    suspend fun getAll(): List<VulnerablePerson>
    suspend fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>)
    suspend fun legend(): Legend
    suspend fun isEmpty(): Boolean
}