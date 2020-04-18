package com.gpillaca.mapa19.data.source

import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson

class LocalDataSourceImpl(
    private val roomDataSource: RoomDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : LocalDataSource {
    override fun getAll(): List<VulnerablePerson> {
        return roomDataSource.getAll()
    }

    override fun insertVulnerablePersons(vulnerablePersons: List<VulnerablePerson>) {
        return roomDataSource.insertVulnerablePersons(vulnerablePersons)
    }

    override fun legend(): Legend {
        return roomDataSource.legend()
    }
}