package com.zzp.hhtally.util

import android.util.Log
import android.widget.Toast
import com.trello.rxlifecycle4.LifecycleTransformer
import com.zzp.hhtally.base.MyApplication
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.network.HttpCallback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


fun Throwable.logE() {
    Log.e(TAG, "Error: ", this)
}

fun String.logD(title: String = "logD") {
    Log.d(TAG, "$title===>: $this")
}

fun String.showToast() {
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_SHORT).show()
}

fun <T : Any> Observable<T>.convert2Main(): Observable<T> =
    this.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.execute(lifecycle: LifecycleTransformer<T>, callback: HttpCallback<T>) =
    this.convert2Main().compose(lifecycle).subscribe(callback)


