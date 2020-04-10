package com.gpillaca.mapa19.map

import com.gpillaca.mapa19.common.presenter.BaseContract

interface MapContract {
    interface View: BaseContract.View {
        fun showMakers(persons: List<VulnerablePerson>)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun loadData()
        fun onInitScope()
        fun onDestroyScope()
    }
}