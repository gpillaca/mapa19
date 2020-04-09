package com.gpillaca.mapa19.common.ui

import androidx.lifecycle.ViewModel
import com.gpillaca.mapa19.common.presenter.BaseContract

class BaseViewModel<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : ViewModel() {

    var presenter: P? = null
        set(value) {
            if (presenter == null) field = value
        }

    override fun onCleared() {
        super.onCleared()
        presenter?.onPresenterDestroy()
        presenter = null
    }
}