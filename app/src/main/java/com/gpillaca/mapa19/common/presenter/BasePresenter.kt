package com.gpillaca.mapa19.common.presenter

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

abstract class BasePresenter<V : BaseContract.View> :
    LifecycleObserver, BaseContract.Presenter<V> {

    private var mStateBundle: Bundle? = null
    private var mView: V? = null

    override fun getView(): V? {
        return mView
    }

    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    override fun attachView(view: V) {
        this.mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun isViewAttached(): Boolean {
        return mView != null
    }

    override fun getStateBundle(): Bundle {
        if (mStateBundle == null) {
            mStateBundle = Bundle()
        }
        return mStateBundle!!
    }

    override fun onPresenterDestroy() {
        mStateBundle?.let {
            if (!it.isEmpty) {
                it.clear()
            }
        }
    }

    override fun onPresenterCreated() {

    }
}