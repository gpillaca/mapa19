package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.data.database.AppDataBase
import com.gpillaca.mapa19.data.toDataBaseVulnerablePersons
import com.gpillaca.mapa19.data.toDomainVulnerablePersons
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

class RoomDataSourceImpl(
    appDataBase: AppDataBase
) : RoomDataSource {

    private val vulnerablePersonDao = appDataBase.vulnerablePersonDao()

    override fun getAll(): List<VulnerablePerson> {
        return vulnerablePersonDao.getVAll().toDomainVulnerablePersons()
    }

    override fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>) {
        vulnerablePersonDao.insertVulnerablePersons(vulnerablePersons.toDataBaseVulnerablePersons())
    }

    override fun legend(): Legend {
        val numHelp: Int = vulnerablePersonDao.getVAll().filter {
            it.estHelp == 2
        }.size

        val numHelped = vulnerablePersonDao.getVAll().size - numHelp
        return Legend(numHelp, numHelped)
    }
}