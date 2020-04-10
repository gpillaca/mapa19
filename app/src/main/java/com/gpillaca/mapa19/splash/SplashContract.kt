package com.gpillaca.mapa19.splash

import com.gpillaca.mapa19.common.presenter.BaseContract

interface SplashContract {
    interface View: BaseContract.View {

    }

    interface Presenter: BaseContract.Presenter<View> {
        fun onLocationRequest(requestCode: Int)
    }
}