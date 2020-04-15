package com.gpillaca.mapa19.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator
import java.util.*

private const val MARKER_DIMENSION = 90

class MarkerClusterRender(
    val context: Context,
    val map: GoogleMap,
    clusterManager: ClusterManager<PersonItem>
) : DefaultClusterRenderer<PersonItem>(context, map, clusterManager) {

    private var iconGenerator: IconGenerator? = null
    private var markerImageView: ImageView? = null

    init {
        iconGenerator = IconGenerator(context)
        markerImageView = ImageView(context)
        markerImageView?.layoutParams = ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION)
        iconGenerator?.setContentView(markerImageView)
    }

    override fun onBeforeClusterItemRendered(item: PersonItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerImageView?.setImageResource(item.getIcon())
        val icon = iconGenerator?.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
        markerOptions.title(item.title)
    }

    override fun onBeforeClusterRendered(
        cluster: Cluster<PersonItem>,
        markerOptions: MarkerOptions
    ) {
        markerOptions.icon(getClusterIcon(cluster))
    }

    override fun onClusterUpdated(cluster: Cluster<PersonItem>, marker: Marker) {
        marker.setIcon(getClusterIcon(cluster))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<PersonItem>): Boolean {
        return cluster.size > 1
    }

    private fun getClusterIcon(cluster: Cluster<PersonItem>): BitmapDescriptor? {
        val profilePhotos: MutableList<Drawable> =
            ArrayList(
                4.coerceAtMost(cluster.size)
            )

        for (item in cluster.items) { // Draw 4 at most.
            if (profilePhotos.size == 4) break
            val drawable: Drawable? = ContextCompat.getDrawable(context, item.getIcon())
            drawable?.setBounds(0, 0, MARKER_DIMENSION, MARKER_DIMENSION)
            drawable?.let { profilePhotos.add(it) }
        }

        val multiDrawable = MultiDrawable(profilePhotos)
        multiDrawable.setBounds(0, 0, MARKER_DIMENSION, MARKER_DIMENSION)
        markerImageView?.setImageDrawable(multiDrawable)
        val icon: Bitmap? = iconGenerator?.makeIcon(cluster.getSize().toString())
        return BitmapDescriptorFactory.fromBitmap(icon)
    }
}