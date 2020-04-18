package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

interface LocalDataSource {
    fun getAll(): List<VulnerablePerson>
    fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>)
    fun legend(): Legend
}