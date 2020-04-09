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

class MapFragment : BaseFragment<MapContract.View, MapContract.Presenter>(), OnMapReadyCallback {

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

    override fun onMapReady(googleMap: GoogleMap?) {
        val sydney = LatLng(-12.0266034, -77.127863)
        googleMap?.addMarker(
            MarkerOptions().position(sydney)
                .title("Marker in Peru")
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}