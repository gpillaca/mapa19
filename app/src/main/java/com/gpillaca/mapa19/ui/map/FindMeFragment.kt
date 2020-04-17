package com.gpillaca.mapa19.ui.map

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.util.defaultConfig
import com.gpillaca.mapa19.databinding.FragmentFindMeBinding
import com.gpillaca.mapa19.databinding.ViewProgressBarBinding
import timber.log.Timber

class FindMeFragment : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraIdleListener {

    private lateinit var map: GoogleMap
    private lateinit var supportMapFragment: SupportMapFragment
    private lateinit var binding: FragmentFindMeBinding
    private lateinit var loadingBinding: ViewProgressBarBinding

    private var circleRadius = 0
    private var isMoving = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindMeBinding.inflate(inflater, container, false)
        loadingBinding = ViewProgressBarBinding.bind(binding.root)
        supportMapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FindMeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportMapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        showMyPosition()
        map.setOnCameraMoveStartedListener(this)
        map.setOnCameraIdleListener(this)
    }

    override fun onCameraMoveStarted(p0: Int) {
        binding.textViewDragMessage.visibility = View.VISIBLE
        isMoving = true
        loadingBinding.progress.visibility = View.GONE
        binding.viewPinCircle.background =
            ContextCompat.getDrawable(loadingBinding.progress.context, R.drawable.circle_background_moving)
        resizeLayout(false)
    }

    override fun onCameraIdle() {
        isMoving = false

            loadingBinding.progress.visibility = View.VISIBLE
        resizeLayout(true)
        binding.textViewDragMessage.visibility = View.GONE

        // TODO refactor
        Handler().postDelayed({
            if (!isMoving) {
                binding.viewPinCircle.background =
                    ContextCompat.getDrawable(loadingBinding.progress.context, R.drawable.circle_background)
                loadingBinding.progress.visibility = View.GONE
            }
        }, 1500)
    }

    private fun showMyPosition() {
        val myPosition = LatLng(-12.1095658, -76.9926256)

        map.defaultConfig().isMyLocationEnabled = false

        val center = CameraUpdateFactory.newLatLng(myPosition)
        val zoom = CameraUpdateFactory.zoomTo(18f)

        map.moveCamera(center)
        map.animateCamera(zoom)
    }

    private fun resizeLayout(backToNormalSize: Boolean) {
        val params = binding.viewPinCircle.layoutParams
        val vto: ViewTreeObserver = binding.viewPinCircle.viewTreeObserver

        circleRadius = binding.viewPinCircle.measuredWidth

        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.viewPinCircle.viewTreeObserver.removeOnGlobalLayoutListener(this)
                circleRadius = binding.viewPinCircle.measuredWidth
            }
        })

        if (backToNormalSize) {
            params.width = WRAP_CONTENT
            params.height = WRAP_CONTENT
        } else {
            params.height = circleRadius - circleRadius / 3
            params.width = circleRadius - circleRadius / 3
            Timber.e("${(circleRadius * 0.3).toInt()}   ${circleRadius - circleRadius / 3}    ")
        }

        binding.viewPinCircle.layoutParams = params
    }
}