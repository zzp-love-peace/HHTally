package com.zzp.hhtally.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.zzp.hhtally.ui.main.MainActivity
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.User
import com.zzp.hhtally.databinding.ActivityLoginBinding
import com.zzp.hhtally.ui.login.register.RegisterDialog
import com.zzp.hhtally.util.showToast

class LoginActivity : BaseActivity<ILoginView, LoginPresenter>(), ILoginView {

    private lateinit var binding: ActivityLoginBinding
    private val prefs by lazy {
        // getPreferences(Context.MODE_PRIVATE)
        getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

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
    }

    override fun initView() {
        binding.apply {
            editName.requestFocus()
            val username = prefs.getString("username", "") ?: ""
            val password = prefs.getString("password", "") ?: ""
            binding.editName.setText(username)
            binding.editPassword.setText(password)
            textRegister.setOnClickListener {
                registerDialog.show()
            }
            fabLogin.setOnClickListener {
//                "点击登录button".showToast()

                presenter.login(
                    editName.text.toString(),
                    editPassword.text.toString()
                )

//                if (result) {
//                    showLoginSuccess("yes")
//                    startActivity(MainActivity::class.java, true)
//                } else {
//                    showLoginFailed("no")
//                }
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

    override fun doSuccess() {
        val editor = prefs.edit()
        val username = binding.editName.text.toString()
        val password = binding.editPassword.text.toString()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
