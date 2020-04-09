package com.gpillaca.mapa19.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.common.ui.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class MapFragment : BaseFragment<MapContract.View, MapContract.Presenter>(),
    MapContract.View,
    OnMapReadyCallback {

    private lateinit var map: GoogleMap

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    private lateinit var supportMapFragment: SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        return view
    }

    override fun initPresenter(): MapContract.Presenter {
        val mPresenter by instance<MapContract.Presenter>()
        return mPresenter
    }

    override fun fragmentModule() = Kodein.Module("fragmentModule") {
        import(mapModule())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.onInitScope()
        supportMapFragment.getMapAsync(this)
        presenter?.loadData()
    }

    override fun showMakers(data: HashMap<String, String>) {
        data.forEach { ( id, latLong)  ->
            val position = latLong.split(',')
            val latitude = position[0].toDouble()
            val longitude = position[1].toDouble()

            setMapLocation(id, latitude, longitude)
        }
    }

    private fun setMapLocation(id: String, latitude: Double, longitude: Double) {
        if (!::map.isInitialized) return

        val position = LatLng(latitude, longitude)

        with(map) {
            addMarker(
                MarkerOptions().position(position)
                    .title("Marker $id in Peru")
            )
            moveCamera(CameraUpdateFactory.newLatLng(position))
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
    }
}