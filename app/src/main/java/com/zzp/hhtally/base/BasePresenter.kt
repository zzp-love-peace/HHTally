package com.zzp.hhtally.base

import java.lang.ref.WeakReference

abstract class BasePresenter<V : BaseView> : IPresenter<V>{

    private var viewRef: WeakReference<V>? = null

    override fun attachView(view: V) {
        viewRef = WeakReference<V>(view)
    }

    override fun detachView() {
        if (viewRef != null) {
            viewRef?.clear()
            viewRef = null
        }
    }

    protected fun isViewAttached(): Boolean {
        return viewRef != null && viewRef?.get() != null
    }

    protected fun getView() :V?{
        return viewRef?.get()
    }

}