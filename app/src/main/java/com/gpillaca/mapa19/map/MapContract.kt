package com.gpillaca.mapa19.map

import android.location.Location
import com.gpillaca.mapa19.common.presenter.BaseContract

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