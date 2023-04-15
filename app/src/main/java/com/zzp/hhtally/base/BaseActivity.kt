package com.zzp.hhtally.base

import android.content.Intent
import android.os.Bundle
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity

abstract class BaseActivity<V : BaseView, P : BasePresenter<V>> : RxAppCompatActivity(), BaseView {
    protected lateinit var presenter: P

    protected abstract fun createPresenter(): P

    protected abstract fun initViewBinding()

    protected abstract fun initData()

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        presenter = createPresenter()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onErrorCode(errorMsg: String) {

    }

    fun startActivity(activity: Class<*>, isFinish: Boolean = false) {
        val intent = Intent(this, activity)
        startActivity(intent)
        if (isFinish) {
            finish()
        }
    }
}