package com.gpillaca.mapa19.data.repository

import com.gpillaca.mapa19.data.convertToPersonItems
import com.gpillaca.mapa19.data.source.LocalDataSource
import com.gpillaca.mapa19.data.source.RemoteDataSource
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.ui.map.PersonItem

class MapRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MapRepository {

    override suspend fun listVulnerablePersons(): List<PersonItem> {
        val vulnerablePersons = remoteDataSource.listVulnerablePersons()
        localDataSource.insertVulnerablePersons(vulnerablePersons)

        return localDataSource.getAll().convertToPersonItems()
    }

    override suspend fun legend(): Legend {
        return localDataSource.legend()
    }
}