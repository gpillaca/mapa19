package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

class InMemoryPersistentStorage : PersistentStorage {

    private var vulnerablePersons: List<VulnerablePerson> = emptyList()

    override fun saveVulnerablePersons(vulnerablePersons: List<VulnerablePerson>) {
        this.vulnerablePersons = vulnerablePersons
    }

    override fun getVulnerablePersons(): List<VulnerablePerson> {
        return vulnerablePersons
    }

    override fun getLegend(): Legend {
        val numHelp: Int = vulnerablePersons.filter {
            it.estHelp == 2
        }.size

        val numHelped = vulnerablePersons.size - numHelp
        return Legend(numHelp, numHelped)
    }
}