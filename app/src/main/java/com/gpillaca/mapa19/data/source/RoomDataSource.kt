package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.data.database.AppDataBase
import com.gpillaca.mapa19.data.toDataBaseVulnerablePersons
import com.gpillaca.mapa19.data.toDomainVulnerablePersons
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

class RoomDataSource(
    appDataBase: AppDataBase
) : DataBaseStorage {

    private val vulnerablePersonDao = appDataBase.vulnerablePersonDao()

    override suspend fun getAll(): List<VulnerablePerson> {
        return vulnerablePersonDao.getAll().map {
            it.toDomainVulnerablePersons()
        }
    }

    override suspend fun isEmpty(): Boolean {
        return vulnerablePersonDao.vulnerablePersonCount() <= 0
    }

    override suspend fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>) {
        vulnerablePersonDao.insertVulnerablePersons(vulnerablePersons.map {
            it.toDataBaseVulnerablePersons()
        })
    }

    override suspend fun legend(): Legend {
        val numHelp: Int = vulnerablePersonDao.getAll().filter {
            it.estHelp == 2
        }.size

        val numHelped = vulnerablePersonDao.getAll().size - numHelp
        return Legend(numHelp, numHelped)
    }
}