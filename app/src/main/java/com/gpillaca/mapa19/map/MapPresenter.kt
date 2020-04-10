package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.common.presenter.BasePresenter
import com.gpillaca.mapa19.common.scope.Scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapPresenter(private val mapRepository: MapRepository) :
    BasePresenter<MapContract.View>(),
    MapContract.Presenter,
    Scope by Scope.Impl() {

    override fun onInitScope() {
        initScope()
    }

    override fun onDestroyScope() {
        destroyScope()
    }

    override fun loadData() {
        launch {
            val persons = withContext(Dispatchers.IO) {
                mapRepository.listVulnerablePersons()
            }

            getView()?.showMakers(persons)
        }
    }
}