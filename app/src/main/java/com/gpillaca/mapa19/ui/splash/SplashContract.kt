package com.gpillaca.mapa19.ui.splash

import com.gpillaca.mapa19.ui.common.BaseContract

interface SplashContract {
    interface View: BaseContract.View {

    }

    interface Presenter: BaseContract.Presenter<View> {
        fun onLocationRequest(requestCode: Int)
    }
}