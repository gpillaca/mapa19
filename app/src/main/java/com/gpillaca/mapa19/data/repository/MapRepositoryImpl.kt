package com.gpillaca.mapa19.data.repository

import com.gpillaca.mapa19.data.convertToPersonItems
import com.gpillaca.mapa19.data.source.DataBaseDataSource
import com.gpillaca.mapa19.data.source.RemoteDataSource
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.ui.map.cluster.PersonItem

class MapRepositoryImpl(
    private val dataBaseDataSource: DataBaseDataSource,
    private val remoteDataSource: RemoteDataSource
) : MapRepository {

    override suspend fun listVulnerablePersons(): List<PersonItem> {
        if (dataBaseDataSource.isEmpty()) {
            val vulnerablePersons = remoteDataSource.listVulnerablePersons()
            dataBaseDataSource.insertVulnerablePersons(vulnerablePersons)
        }

        return dataBaseDataSource.getAll().map {
            it.convertToPersonItems()
        }
    }

    override suspend fun legend(): Legend {
        return dataBaseDataSource.legend()
    }
}