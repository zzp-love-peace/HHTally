package com.zzp.hhtally.ui.login.register

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.databinding.DialogRegisterAccountBinding
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.ui.login.LoginActivity
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.showToast

class RegisterDialog(activity: LoginActivity) {

    private val dialogRegisterAccountBinding: DialogRegisterAccountBinding =
        DialogRegisterAccountBinding.inflate(LayoutInflater.from(activity))

    private val registerDialog by lazy {
        MaterialAlertDialogBuilder(activity)
            .setView(dialogRegisterAccountBinding.root)
            .create()
    }

    init {
        dialogRegisterAccountBinding.apply {
            fabRegister.setOnClickListener {
                val name = editName.text?.trim().toString()
                val password = editPassword.text?.trim().toString()
                val confirmPassword = editConfirmPassword.text?.trim().toString()
                if (checkRegisterStatus(name, password, confirmPassword)) {
                    dialogRegisterAccountBinding.loadingProgress.isVisible = true
                    RetrofitManager.apiService.register(name, password).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>(){
                        override fun onSuccess(model: HttpResult<Any?>) {
                            if (model.code == 200) {
                                registerDialog.dismiss()
                            }
                            model.msg.showToast()
                            dialogRegisterAccountBinding.loadingProgress.isVisible = false
                        }
                    })
                }
            }
            editName.doAfterTextChanged {
                if (tilName.error.isNullOrBlank().not())
                    tilName.error = ""
            }
            editPassword.doAfterTextChanged {
                if (tilPassword.error.isNullOrBlank().not())
                    tilPassword.error = ""
            }
            editConfirmPassword.doAfterTextChanged {
                if (tilConfirmPassword.error.isNullOrBlank().not())
                    tilConfirmPassword.error = ""
            }
        }
    }

    fun show() {
        registerDialog.show()
    }

    private fun checkRegisterStatus(
        name: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.length < 3) {
            dialogRegisterAccountBinding.tilName.error = "用户名至少3位"
            return false
        }
        if (password.length < 6) {
            dialogRegisterAccountBinding.tilPassword.error = "密码至少 6 位"
            return false
        }
        if (confirmPassword.length < 6) {
            dialogRegisterAccountBinding.tilConfirmPassword.error = "密码至少 6 位"
            return false
        }
        if (password != confirmPassword) {
            dialogRegisterAccountBinding.tilConfirmPassword.error = "确认密码与密码不符"
            return false
        }
        return true
    }

}
