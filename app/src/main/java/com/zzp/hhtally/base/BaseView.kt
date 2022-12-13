package com.zzp.hhtally.base

import com.zzp.hhtally.network.HttpResult

interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun onErrorCode(errorMsg: String)

}