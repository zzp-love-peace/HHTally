package com.zzp.hhtally.ui.login

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.*


class LoginPresenter(baseView: ILoginView) : BasePresenter<ILoginView>(baseView) {

    fun login(username: String, password: String) {
        if (!isValid(username, password)) {
            "用户名或密码不合法".showToast()
            return
        }
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.login(username, password)
            .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<String>>() {
                override fun onSuccess(model: HttpResult<String>) {
                    if (model.code == 200) {
                        view.showLoginSuccess(model.msg)
                        view.doSuccess()
                        UserData.token = model.data
                    }
                    model.msg.showToast()
                }
            })

    }

    //判断用户名与密码是否合法
    private fun isValid(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}
