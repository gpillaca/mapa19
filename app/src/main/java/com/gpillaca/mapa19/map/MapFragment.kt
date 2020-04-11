package com.gpillaca.mapa19.map

import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.common.fromJsonStringTo
import com.gpillaca.mapa19.common.toJsonString
import com.gpillaca.mapa19.common.ui.BaseFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class PersonItem(private var position: LatLng, private var title: String, private var snippet: String) : ClusterItem {
    override fun getSnippet(): String {
        return snippet
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPosition(): LatLng {
        return position
    }
}

class MapFragment : BaseFragment<MapContract.View, MapContract.Presenter>(),
    MapContract.View,
    ClusterManager.OnClusterClickListener<PersonItem>,
    View.OnClickListener,
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var mClusterManager: ClusterManager<MyItem>
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
    }

    override fun showMakers(persons: List<VulnerablePerson>) {

        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)

        mClusterManager = ClusterManager<MyItem>(activity, map)
        // mClusterManager.setAlgorithm(
        // NonHierarchicalViewBasedAlgorithm<MyItem>(
        // metrics.widthPixels, metrics.heightPixels
        // )
        // )

        map.setOnCameraIdleListener(mClusterManager)
        val d = mutableListOf<MyItem>()
        persons.map { person ->
            mClusterManager.addItem(MyItem(person.positionLatLng, "", ""))
            //setMapLocation(person)
        }

    }

    private fun setMapLocation(person: VulnerablePerson) = with(person) {
        if (!::map.isInitialized) return@with

        with(map) {
            addMarker(
                MarkerOptions()
                    .position(positionLatLng)
                    .anchor(0f, 1f)
                    .icon(addMaker(estHelp))
                    .title(person.toJsonString())
            )
        }
    }

    override fun showMyPosition(location: Location) {
        //val myPosition = LatLng(-12.0266034, -77.127863)
        val myPosition = LatLng(location.latitude, location.longitude)
        val cameraPosition: CameraPosition = CameraPosition.Builder()
            .target(myPosition)
            .zoom(13F)
            .bearing(30f)
            .tilt(45F)
            .build()

        map.uiSettings.isMyLocationButtonEnabled = false
        map.isMyLocationEnabled = true
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMapToolbarEnabled = false
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        presenter?.loadData()
        map.setOnMarkerClickListener(this)
    }

    override fun onClusterClick(cluster: Cluster<PersonItem>?): Boolean {
        /*val vulnerablePerson = marker.title.fromJsonStringTo<VulnerablePerson>()
        val bottomSheet = PersonBottomSheet.newInstance(vulnerablePerson)

        activity?.let {
            bottomSheet.show(it.supportFragmentManager, bottomSheet.tag)
        }*/

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

    override fun onDestroyView() {
        presenter?.onDestroyScope()
        super.onDestroyView()
    }
}