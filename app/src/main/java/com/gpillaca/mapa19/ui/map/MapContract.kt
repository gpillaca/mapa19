package com.gpillaca.mapa19.ui.map

import android.location.Location
import com.gpillaca.mapa19.domain.Legend
import com.gpillaca.mapa19.ui.common.BaseContract
import com.gpillaca.mapa19.ui.map.cluster.PersonItem

interface MapContract {
    interface View: BaseContract.View {
        fun showMakers(persons: List<PersonItem>)
        fun showMyPosition(location: Location)
        fun showLegend(legend: Legend)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun showMyPosition()
        fun loadData()
    }
}