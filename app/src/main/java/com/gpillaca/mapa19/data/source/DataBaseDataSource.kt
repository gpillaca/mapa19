package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

interface DataBaseDataSource {
    fun getAll(): List<VulnerablePerson>
    fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>)
    fun legend(): Legend
    fun isEmpty(): Boolean
}