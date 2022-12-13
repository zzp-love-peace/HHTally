package com.zzp.hhtally.ui.login

import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.base.BaseView

class LoginPresenter(baseView: ILoginView) : BasePresenter<ILoginView>() {

    init {
        attachView(baseView)
    }

    fun login(username: String, password: String) {

    }

    //判断用户名与密码是否合法
    private fun isValid(username: String, password: String): Boolean {
        return false
    }
}