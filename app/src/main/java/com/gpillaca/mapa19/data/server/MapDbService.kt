package com.gpillaca.mapa19.data.server

import retrofit2.http.Headers
import retrofit2.http.POST

interface MapDbService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("listado_mapbd")
    suspend fun listData(): MapDbResult
}