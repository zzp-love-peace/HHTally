package com.zzp.hhtally.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle4.components.support.RxFragment

abstract class BaseFragment<V:BaseView, P : BasePresenter<V>>: RxFragment(), BaseView {
    protected lateinit var presenter: P

    protected abstract fun createPresenter(): P

    protected abstract fun initViewBinding(inflater: LayoutInflater,
                                           container: ViewGroup?,): View

    protected abstract fun initData()

    protected abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter = createPresenter()
        return initViewBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onErrorCode(errorMsg: String) {

    }
}