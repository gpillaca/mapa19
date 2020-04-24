package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

interface PersistentStorage {
    fun saveVulnerablePersons(vulnerablePersons: List<VulnerablePerson>)
    fun getVulnerablePersons(): List<VulnerablePerson>
    fun getLegend(): Legend
}