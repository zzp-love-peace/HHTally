package com.zzp.hhtally.ui.login

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.User

interface ILoginView : BaseView{

    fun showLoginSuccess(successMsg:String)

    fun showLoginFailed(errorMsg: String)

    fun doSuccess(user: User)
}