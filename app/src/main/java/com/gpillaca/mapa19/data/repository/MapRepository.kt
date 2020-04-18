package com.gpillaca.mapa19.data.repository

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.ui.map.PersonItem

interface MapRepository {
    suspend fun listVulnerablePersons(): List<PersonItem>
    suspend fun legend(): Legend
}