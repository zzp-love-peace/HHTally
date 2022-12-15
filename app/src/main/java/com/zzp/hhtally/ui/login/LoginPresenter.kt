package com.zzp.hhtally.ui.login

import com.zzp.hhtally.base.BasePresenter


class LoginPresenter(baseView: ILoginView) : BasePresenter<ILoginView>(baseView) {

    fun login(username: String, password: String) : Boolean {
        return true
    }

    //判断用户名与密码是否合法
    private fun isValid(username: String, password: String): Boolean {
        return false
    }
}
