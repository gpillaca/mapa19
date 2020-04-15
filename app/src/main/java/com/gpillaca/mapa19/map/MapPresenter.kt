package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.common.presenter.BasePresenter
import com.gpillaca.mapa19.common.scope.Scope
import com.gpillaca.mapa19.splash.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapPresenter(
    private val mapRepository: MapRepository,
    private val locationRepository: LocationRepository
) : BasePresenter<MapContract.View>(),
    MapContract.Presenter,
    Scope by Scope.Impl()
{

    override fun onInitScope() {
        initScope()
    }

    override fun onDestroyScope() {
        destroyScope()
    }

    override fun loadData() {
        launch {
            getView()?.showLoading()
            val persons = async(Dispatchers.IO) {
                mapRepository.listVulnerablePersons()
            }

            val myPosition = async(Dispatchers.IO) {
                locationRepository.myPosition()
            }

            getView()?.showMyPosition(myPosition.await())
            getView()?.showMakers(persons.await())
            getView()?.hideLoading()
        }
    }

    override fun showMyPosition() {
        launch {
            val myPosition = withContext(Dispatchers.IO) {
                locationRepository.myPosition()
            }

            getView()?.showMyPosition(myPosition)
        }
    }
}