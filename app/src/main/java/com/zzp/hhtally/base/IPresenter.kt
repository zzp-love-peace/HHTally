package com.zzp.hhtally.base

interface IPresenter<V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}