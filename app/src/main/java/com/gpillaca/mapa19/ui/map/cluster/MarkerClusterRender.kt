package com.gpillaca.mapa19.ui.map.cluster

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

private const val MARKER_DIMENSION = 90

class MarkerClusterRender(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<PersonItem>
) : DefaultClusterRenderer<PersonItem>(context, map, clusterManager) {

    private var iconGenerator: IconGenerator? = null
    private var markerImageView: ImageView? = null

    init {
        iconGenerator = IconGenerator(context)
        markerImageView = ImageView(context)
        markerImageView?.layoutParams = ViewGroup.LayoutParams(
            MARKER_DIMENSION,
            MARKER_DIMENSION
        )
        iconGenerator?.setContentView(markerImageView)
    }

    override fun onBeforeClusterItemRendered(item: PersonItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerImageView?.setImageResource(item.getIcon())
        val icon = iconGenerator?.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item.title)
    }
}