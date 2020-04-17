package com.gpillaca.mapa19.ui.map

import android.location.Location
import com.gpillaca.mapa19.ui.common.BaseContract

interface MapContract {
    interface View: BaseContract.View {
        fun showMakers(persons: List<PersonItem>)
        fun showMyPosition(location: Location)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun showMyPosition()
        fun loadData()
        fun onInitScope()
        fun onDestroyScope()
    }
}