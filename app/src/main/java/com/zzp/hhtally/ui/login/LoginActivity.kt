package com.zzp.hhtally.ui.login

import com.zzp.hhtally.MainActivity
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.User
import com.zzp.hhtally.databinding.ActivityLoginBinding
import com.zzp.hhtally.ui.login.register.RegisterDialog
import com.zzp.hhtally.util.showToast

class LoginActivity : BaseActivity<ILoginView, LoginPresenter>(), ILoginView {

    private lateinit var binding: ActivityLoginBinding

    private val registerDialog by lazy {
        RegisterDialog(this)
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun initViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun initData() {
        // 登录数据可保存用户名自动填充

    }

    override fun initView() {
        binding.apply {
            editName.requestFocus()
            textRegister.setOnClickListener {
                registerDialog.show()
            }
            fabLogin.setOnClickListener {
                "点击登录button".showToast()
                val result = presenter.login(
                    editName.text.toString(),
                    editPassword.text.toString()
                )
                if (result) {
                    showLoginSuccess("yes")
                    startActivity(MainActivity::class.java, true)
                } else {
                    showLoginFailed("no")
                }
            }
        }
    }

    override fun showLoginSuccess(successMsg: String) {

    }

    override fun showLoginFailed(errorMsg: String) {
        binding.apply {
            tilName.error = getString(R.string.hint_login_error)
            tilPassword.error = getString(R.string.hint_login_error)
        }
    }

    override fun doSuccess(user: User) {
        TODO("Not yet implemented")
    }

}
