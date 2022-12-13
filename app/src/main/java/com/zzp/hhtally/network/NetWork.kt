package com.zzp.hhtally.network

import com.trello.rxlifecycle4.LifecycleTransformer
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


object NetWork {

    private val httpService = RetrofitManager.create<NetworkService>()

}

