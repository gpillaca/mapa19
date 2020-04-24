package com.gpillaca.mapa19.data.source

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.data.server.MapDbResult
import com.gpillaca.mapa19.domain.VulnerablePerson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.util.*

private const val REGEX_INPUT_BOUNDARY_BEGINNING = "\\A"

class FakeRetrofitDataSource(
    private val context: Context
) : RemoteDataSource {
    override suspend fun listVulnerablePersons(): List<VulnerablePerson> {
        val persons = mutableListOf<VulnerablePerson>()
        val dataResponse = readData()
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

        return persons
    }

    @Throws(JSONException::class)
    fun readData(): MapDbResult {
        val inputStream: InputStream = context.resources.openRawResource(R.raw.info_map)
        val json = Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next()

        var responseJsonObject = JSONObject(json.toString())
        val mapDbResult = MapDbResult(
            res = responseJsonObject.getString("res"),
            dataLatLong = getData(responseJsonObject, "data_latlong"),
            dataNames = getData(responseJsonObject, "data_names"),
            dataDni = getData(responseJsonObject, "data_dni"),
            dataPhoneNumbers = getData(responseJsonObject, "data_cel"),
            dataMessage = getData(responseJsonObject, "data_des"),
            dataType = getData(responseJsonObject, "data_tipo"),
            dataNoRep = getData(responseJsonObject, "data_norep"),
            dataEstHelp = getData(responseJsonObject, "data_esthelp"),
            dataNHelp = getData(responseJsonObject, "data_nhelp")
        )
        return mapDbResult
    }

    private fun getData(jsonObject: JSONObject, key: String): HashMap<String, String> {
        val data = HashMap<String, String>()
        val tempData = jsonObject.get(key) as JSONArray

        for (i in 0 until tempData.length()) {
            if (tempData[i] is String) {
                continue
            }

            data[i.toString()] = tempData.getJSONArray(i)[0].toString()
        }

        return data
    }
}