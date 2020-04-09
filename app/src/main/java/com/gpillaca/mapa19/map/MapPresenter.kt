package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.common.presenter.BasePresenter

class MapPresenter(private val mapRepository: MapRepository) :
    BasePresenter<MapContract.View>(),
    MapContract.Presenter {

}