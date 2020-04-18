package com.gpillaca.mapa19.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MapDbResult(
    val res: String,
    @SerializedName("data_latlong") val dataLatLong: HashMap<String, String>,
    @SerializedName("data_names") val dataNames: HashMap<String, String>,
    @SerializedName("data_dni") val dataDni: HashMap<String, String>,
    @SerializedName("data_cel") val dataPhoneNumbers: HashMap<String, String>,
    @SerializedName("data_des") val dataMessage: HashMap<String, String> ,
    @SerializedName("data_tipo") val dataType: HashMap<String, String>,
    @SerializedName("data_norep") val dataNoRep: HashMap<String, String>,
    @SerializedName("data_esthelp") val dataEstHelp: HashMap<String, String>,
    @SerializedName("data_nhelp") val dataNHelp: HashMap<String, String>
) : Parcelable