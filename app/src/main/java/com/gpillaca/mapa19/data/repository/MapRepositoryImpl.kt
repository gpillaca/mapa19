package com.gpillaca.mapa19.data.repository

import com.google.android.gms.maps.model.LatLng
import com.gpillaca.mapa19.data.convertToPersonItems
import com.gpillaca.mapa19.data.server.MapDbResult
import com.gpillaca.mapa19.data.server.MapDbService
import com.gpillaca.mapa19.ui.map.MapRepository
import com.gpillaca.mapa19.ui.map.PersonItem
import com.gpillaca.mapa19.ui.map.VulnerablePerson
import retrofit2.Retrofit

class MapRepositoryImpl(private val retrofit: Retrofit):
    MapRepository {

    override suspend fun listVulnerablePersons(): List<PersonItem> {
        val dataResponse: MapDbResult = retrofit.create(MapDbService::class.java).listData()
        val persons = mutableListOf<VulnerablePerson>()

        dataResponse.dataNames.forEach { (id, name) ->
            val position =  dataResponse.dataLatLong.getValue(id).split(',')
            val latitude =  position[0].toDouble()
            val longitude = position[1].toDouble()

            val person = VulnerablePerson(
                id = id,
                positionLatLng = LatLng(latitude, longitude),
                name = name,
                dni = dataResponse.dataDni.getValue(id),
                phoneNumber = dataResponse.dataPhoneNumbers.getValue(id),
                message = dataResponse.dataMessage.getValue(id),
                type = dataResponse.dataType.getValue(id).toInt(),
                estHelp = dataResponse.dataEstHelp.getValue(id).toInt(),
                nHelp = dataResponse.dataNHelp.getValue(id).toInt(),
                noRep = dataResponse.dataNoRep.getValue(id).toInt()
            )

            persons.add(person)
        }

        return persons.convertToPersonItems()
    }
}