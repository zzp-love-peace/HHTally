package com.zzp.hhtally.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object NetWork {

    private val httpService = RetrofitManager.create<NetworkService>()


    private fun <T> Observable<T>.convert2Main() =
        this.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun <T> Observable<T>.execute(callback: HttpCallback<T>) {
        this.convert2Main().subscribe(callback)
    }

}