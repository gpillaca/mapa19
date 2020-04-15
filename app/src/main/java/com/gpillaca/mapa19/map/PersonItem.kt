package com.gpillaca.mapa19.map

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class PersonItem(
    private val position: LatLng,
    private val title: String,
    private val snippet: String,
    private val icon: Int
) : ClusterItem {
    override fun getSnippet(): String {
        return snippet
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPosition(): LatLng {
        return position
    }

    fun getIcon(): Int {
        return icon
    }
}