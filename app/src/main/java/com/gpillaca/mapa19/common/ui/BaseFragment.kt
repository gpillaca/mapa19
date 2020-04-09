package com.gpillaca.mapa19.common.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.gpillaca.mapa19.common.presenter.BaseContract
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.subKodein
import org.kodein.di.android.x.kodein

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : Fragment(),
    BaseContract.View, KodeinAware {

    protected var presenter: P? = null

    override val kodein by subKodein(kodein()) {
        import(fragmentModule())
    }

    /**
     * Optional to override, if your fragment needs specific DI.
     */
    open fun fragmentModule() = Kodein.Module("fragmentModule") {
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        viewModel as BaseViewModel<V, P>
        var isPresenterCreated = false
        if (viewModel.presenter == null) {
            viewModel.presenter = initPresenter()
            isPresenterCreated = true
        }
        presenter = viewModel.presenter as P
        presenter!!.attachLifecycle(lifecycle)
        presenter!!.attachView(this as V)
        if (isPresenterCreated)
            presenter!!.onPresenterCreated()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter!!.detachLifecycle(lifecycle)
        presenter!!.detachView()
    }

    protected abstract fun initPresenter(): P
}