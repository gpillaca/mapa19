package com.gpillaca.mapa19.ui.splash

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.di.splashModule
import com.gpillaca.mapa19.ui.common.BaseFragment
import com.gpillaca.mapa19.util.PermissionHelper
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private const val REQUEST_LOCATION = 1

class SplashFragment : BaseFragment<SplashContract.View, SplashContract.Presenter>(),
    SplashContract.View {

    private val permissionHelper by instance<PermissionHelper>()
    private var listener: ActionListener? = null

    interface ActionListener {
        fun navigateToActivity()
    }

    override fun initPresenter(): SplashContract.Presenter {
        val mPresenter by instance<SplashContract.Presenter>()
        return mPresenter
    }

    override fun fragmentModule() = Kodein.Module("fragmentModule") {
        import(splashModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_splash, container, false)

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission(REQUEST_LOCATION)
    }

    private fun requestLocationPermission(requestCode: Int) {
        context?.let {
            if (permissionHelper.locationPermissionNotGranted(it)) {
                permissionHelper.requestLocationPermission(requestCode, this)
                return
            }

            listener?.navigateToActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            if (requestCode == REQUEST_LOCATION) {
                listener?.navigateToActivity()
            }
        } else {
            requestLocationPermission(REQUEST_LOCATION)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionListener) {
            listener = context as ActionListener
        }
    }
}