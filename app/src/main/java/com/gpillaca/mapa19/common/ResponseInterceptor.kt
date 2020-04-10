package com.gpillaca.mapa19.common

import com.google.gson.Gson
import com.gpillaca.mapa19.BuildConfig
import com.gpillaca.mapa19.server.MapDbResult
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

fun loggingInterceptor(): HttpLoggingInterceptor {
    return if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
}

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        try {
            if (response.code == 200 && response.body != null) {
                val responseBody = response.body!!
                var responseJsonObject = JSONObject(responseBody.string())
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

                responseJsonObject = JSONObject(Gson().toJson(mapDbResult))
                val contentType: MediaType? = responseBody.contentType()
                val body: ResponseBody = responseJsonObject.toString().toResponseBody(contentType)

                return response.newBuilder().body(body).build()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }

        return response
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