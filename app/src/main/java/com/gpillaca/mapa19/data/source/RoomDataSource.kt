package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.data.database.AppDataBase
import com.gpillaca.mapa19.data.toDataBaseVulnerablePersons
import com.gpillaca.mapa19.data.toDomainVulnerablePersons
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

class RoomDataSource(
    appDataBase: AppDataBase
) : DataBaseDataSource {

    private val vulnerablePersonDao = appDataBase.vulnerablePersonDao()

    override fun getAll(): List<VulnerablePerson> {
        return vulnerablePersonDao.getAll().map {
            it.toDomainVulnerablePersons()
        }
    }

    override fun isEmpty(): Boolean {
        return vulnerablePersonDao.vulnerablePersonCount() <= 0
    }

    override fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>) {
        vulnerablePersonDao.insertVulnerablePersons(vulnerablePersons.map {
            it.toDataBaseVulnerablePersons()
        })
    }

    override fun legend(): Legend {
        val numHelp: Int = vulnerablePersonDao.getAll().filter {
            it.estHelp == 2
        }.size

        val numHelped = vulnerablePersonDao.getAll().size - numHelp
        return Legend(numHelp, numHelped)
    }
}