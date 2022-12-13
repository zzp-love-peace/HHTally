package com.zzp.hhtally.network

import com.zzp.hhtally.util.logE
import com.zzp.hhtally.util.showToast
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.observers.DisposableObserver

import retrofit2.HttpException


abstract class HttpCallback<T : Any>: DisposableObserver<T>() {

    abstract fun onSuccess(model: T)
    
    private fun onFailure(msg: String) {
        msg.showToast()
    }
    
    private fun onFinish() {

    }

    override fun onError(e: Throwable) {
        e.logE()
        if (e is HttpException) {
            val code = e.code()
            var msg = e.message()
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 504) {
                msg = "服务器异常，请稍后再试"
            }
            onFailure(msg)
        } else {
            onFailure(e.message ?: "错误信息为空")
        }
        onFinish()
    }
    
    override fun onNext(value: T) {
        onSuccess(value)
    }
    

    override fun onComplete() {
        onFinish()
    }
}