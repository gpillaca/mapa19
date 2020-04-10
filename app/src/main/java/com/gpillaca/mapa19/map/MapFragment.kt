package com.gpillaca.mapa19.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.common.ui.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class MapFragment : BaseFragment<MapContract.View, MapContract.Presenter>(),
    MapContract.View,
    GoogleMap.OnMarkerClickListener,
    View.OnClickListener,
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
        supportMapFragment.getMapAsync(this)
        presenter?.onInitScope()
        presenter?.loadData()
    }

    override fun showMakers(persons: List<VulnerablePerson>) {
        persons.forEach { person ->
            setMapLocation(person)
        }
    }

    private fun setMapLocation(person: VulnerablePerson) = with(person) {
        if (!::map.isInitialized) return@with

        val position = LatLng(latitude, longitude)
        val title = Gson().toJson(person)

        with(map) {
            addMarker(
                MarkerOptions()
                    .position(position)
                    .anchor(0f, 1f)
                    .icon(addMaker(estHelp))
                    .title(title)
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        map.setOnMarkerClickListener(this)

        //TODO get my position
        val myPosition = LatLng(-12.0266034, -77.127863)
        val cameraPosition: CameraPosition = CameraPosition.Builder()
            .target(myPosition)
            .zoom(14F)
            .bearing(30f)
            .tilt(45F)
            .build()

        map.uiSettings.isMyLocationButtonEnabled = false
        //map.isMyLocationEnabled = true //TODO request permission
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map.addMarker(
            MarkerOptions()
                .position(myPosition)
                .title("Marker in Peru")
        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val vulnerablePerson: VulnerablePerson = Gson().fromJson(marker.title, VulnerablePerson::class.java)
        val bottomSheet = PersonBottomSheet.newInstance(vulnerablePerson)

        activity?.let {
            bottomSheet.show(it.supportFragmentManager, bottomSheet.tag)
        }

        return true
    }

    override fun onClick(v: View?) {
        val id = v?.id ?: 0

        when (id) {
            R.id.buttonHelp -> {
                //TODO navigate to next activity
            }
        }
    }

    private fun addMaker(type: Int): BitmapDescriptor {
        val marker = if (type == 2) {
            R.drawable.ic_help
        } else {
            R.drawable.ic_helped
        }

        return BitmapDescriptorFactory.fromResource(marker)
    }
}