package com.gpillaca.mapa19.data.repository

import com.gpillaca.mapa19.data.convertToPersonItems
import com.gpillaca.mapa19.data.source.PersistentStorage
import com.gpillaca.mapa19.data.source.RemoteDataSource
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.ui.map.cluster.PersonItem

class MapRepositoryImpl(
    private val persistentStorage: PersistentStorage,
    private val remoteDataSource: RemoteDataSource
) : MapRepository {

    override suspend fun listVulnerablePersons(): List<PersonItem> {
        if (persistentStorage.getVulnerablePersons().isEmpty()) {
            val vulnerablePersons = remoteDataSource.listVulnerablePersons()
            persistentStorage.saveVulnerablePersons(vulnerablePersons)
        }

        return persistentStorage.getVulnerablePersons().map {
            it.convertToPersonItems()
        }
    }

    override suspend fun legend(): Legend {
        return persistentStorage.getLegend()
    }
}