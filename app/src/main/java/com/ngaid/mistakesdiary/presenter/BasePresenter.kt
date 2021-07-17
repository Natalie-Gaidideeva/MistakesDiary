package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.interfaces.BaseContract

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    protected var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDestroy() {
        view = null
    }
}