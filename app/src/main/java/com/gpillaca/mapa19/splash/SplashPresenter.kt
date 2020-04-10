package com.gpillaca.mapa19.splash

import com.gpillaca.mapa19.common.presenter.BasePresenter
import com.gpillaca.mapa19.common.scope.Scope

class SplashPresenter :
    BasePresenter<SplashContract.View>(), SplashContract.Presenter,
    Scope by Scope.Impl()
{
    override fun onLocationRequest(requestCode: Int) {

    }
}