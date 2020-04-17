package com.gpillaca.mapa19.ui.common

import android.os.Bundle
import androidx.lifecycle.Lifecycle

interface BaseContract {
    interface View

    interface Presenter<V : View> {
        fun attachLifecycle(lifecycle: Lifecycle)
        fun detachLifecycle(lifecycle: Lifecycle)
        fun attachView(view: V)
        fun detachView()
        fun getView(): V?
        fun isViewAttached(): Boolean
        fun getStateBundle(): Bundle
        fun onPresenterDestroy()
        fun onPresenterCreated()
    }
}