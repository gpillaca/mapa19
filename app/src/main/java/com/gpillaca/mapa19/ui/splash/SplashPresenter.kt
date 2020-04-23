package com.gpillaca.mapa19.ui.splash

import com.gpillaca.mapa19.ui.common.BasePresenter
import com.gpillaca.mapa19.ui.common.Scope

class SplashPresenter :
    BasePresenter<SplashContract.View>(), SplashContract.Presenter,
    Scope by Scope.Impl() {

}