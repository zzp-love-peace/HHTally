package com.zzp.hhtally.network

import android.util.Log
import com.zzp.hhtally.util.logE
import com.zzp.hhtally.util.showToast
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.observers.DisposableObserver

import retrofit2.HttpException


abstract class HttpCallback<T>: DisposableObserver<T>() {

    abstract fun onSuccess(model: T)
    
    private fun onFailure(msg: String) {
        msg.showToast()
    }
    
    private fun onFinish() {

    }

    override fun onError(e: Throwable) {
        e.logE()
        if (e is HttpException) {
            Log.d("TAG", "onError: ${e.response()?.errorBody()?.string()}")
            val code = e.code()
            var msg = e.message()
            if (code == 504) {
                msg = "网络不给力"
            }
            if (code == 502 || code == 504) {
                msg = "服务器异常，请稍后再试"
            }
            if (code == 404) {
                msg = "客户端错误"
            }
            onFailure(msg)
        } else {
            onFailure(e.message ?: "错误信息为空")
        }
        onFinish()
    }
    
    override fun onNext(value: T) {
        Log.d("TAG", "onNext: $value")
        onSuccess(value)
    }
    

    override fun onComplete() {
        onFinish()
    }
}