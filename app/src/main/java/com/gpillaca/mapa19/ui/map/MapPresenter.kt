package com.gpillaca.mapa19.ui.map

import com.gpillaca.mapa19.ui.common.BasePresenter
import com.gpillaca.mapa19.ui.common.Scope
import com.gpillaca.mapa19.data.repository.LocationRepository
import com.gpillaca.mapa19.data.repository.MapRepository
import com.gpillaca.mapa19.ui.map.cluster.PersonItem
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

    override fun onCreateScope() {
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
            showMarkers(persons.await())
        }
    }

    private suspend fun showMarkers(persons: List<PersonItem>) {
        getView()?.showMakers(persons)

        val legend = withContext(Dispatchers.IO) {
            mapRepository.legend()
        }

        getView()?.showLegend(legend)
        getView()?.hideLoading()
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