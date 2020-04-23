package com.gpillaca.mapa19.ui.map

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.NonHierarchicalViewBasedAlgorithm
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.databinding.FragmentMapBinding
import com.gpillaca.mapa19.databinding.ViewProgressBarBinding
import com.gpillaca.mapa19.di.mapModule
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.domain.VulnerablePerson
import com.gpillaca.mapa19.ui.common.BaseFragment
import com.gpillaca.mapa19.ui.map.cluster.MarkerClusterRender
import com.gpillaca.mapa19.ui.map.cluster.PersonItem
import com.gpillaca.mapa19.util.defaultConfig
import com.gpillaca.mapa19.util.fromJsonStringTo
import com.gpillaca.mapa19.util.toJsonString
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class MapFragment : BaseFragment<MapContract.View, MapContract.Presenter>(),
    MapContract.View,
    ClusterManager.OnClusterClickListener<PersonItem>,
    View.OnClickListener,
    ClusterManager.OnClusterItemClickListener<PersonItem>,
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var clusterManager: ClusterManager<PersonItem>
    private var listener: ActionListener? = null
    private lateinit var binding: FragmentMapBinding
    private lateinit var loadingBinding: ViewProgressBarBinding
    private var isRestore = false

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    interface ActionListener {
        fun navigateToFindMe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRestore = savedInstanceState != null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        loadingBinding = ViewProgressBarBinding.bind(binding.root)
        return binding.root
    }

    override fun initPresenter(): MapContract.Presenter {
        val mPresenter by instance<MapContract.Presenter>()
        return mPresenter
    }

    override fun fragmentModule() = Kodein.Module("fragmentModule") {
        import(mapModule())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.onCreateScope()
        binding.buttonHelp.setOnClickListener(this)
    }

    override fun showMakers(persons: List<PersonItem>) {
        setUpClusterManager(persons)
    }

    private fun setUpClusterManager(persons: List<PersonItem>) {
        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        clusterManager = ClusterManager(context, map)
        clusterManager.setAlgorithm(
            NonHierarchicalViewBasedAlgorithm<PersonItem>(
                metrics.widthPixels,
                metrics.heightPixels
            )
        )

        context?.let { clusterManager.renderer =
            MarkerClusterRender(
                it,
                map,
                clusterManager
            )
        }
        clusterManager.setOnClusterItemClickListener(this)
        map.setOnCameraIdleListener(clusterManager)
        map.setOnMarkerClickListener(clusterManager)

        clusterManager.addItems(persons)
        clusterManager.cluster()
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

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun showMyPosition(location: Location) {
        map.defaultConfig()

        if (!isRestore) {
            val myPosition = LatLng(location.latitude, location.longitude)
            val cameraPosition: CameraPosition = CameraPosition.Builder()
                .target(myPosition)
                .zoom(13F)
                .bearing(30f)
                .tilt(45F)
                .build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        presenter?.loadData()
    }

    override fun showLoading() {
        loadingBinding.progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingBinding.progress.visibility = View.GONE
    }

    override fun onClusterClick(cluster: Cluster<PersonItem>?): Boolean {
        return true
    }

    override fun onClusterItemClick(item: PersonItem): Boolean {
        val vulnerablePerson = item.title.fromJsonStringTo<VulnerablePerson>()
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
                listener?.navigateToFindMe()
            }
        }
    }

    override fun showLegend(legend: Legend) {
        binding.cardViewLegend.visibility = View.VISIBLE
        binding.textViewHelp.text = legend.numHelp.toString()
        binding.textViewHelped.text = legend.numHelped.toString()
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
        binding.mapView.onDestroy()
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ActionListener) {
            listener = context
        }
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }
}