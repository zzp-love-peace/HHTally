package com.zzp.hhtally.network

import com.trello.rxlifecycle4.LifecycleTransformer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


object NetWork {

    private val httpService = RetrofitManager.create<NetworkService>()

    private fun <T : Any> Observable<T>.convert2Main() =
        this.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun <T : Any> Observable<T>.execute(lifecycle: LifecycleTransformer<T>, callback: HttpCallback<T>) =
        this.convert2Main().compose(lifecycle).subscribe(callback)
}