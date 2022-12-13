package com.zzp.hhtally.ui.login

import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.User
import com.zzp.hhtally.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ILoginView, LoginPresenter>(), ILoginView {

    private lateinit var binding: ActivityLoginBinding

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun initViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun showLoginSuccess(successMsg: String) {
        TODO("Not yet implemented")
    }

    override fun showLoginFailed(errorMsg: String) {
        TODO("Not yet implemented")
    }

    override fun doSuccess(user: User) {
        TODO("Not yet implemented")
    }

}