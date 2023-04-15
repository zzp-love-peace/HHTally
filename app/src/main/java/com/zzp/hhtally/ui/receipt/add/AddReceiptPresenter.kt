package com.zzp.hhtally.ui.receipt.add

import com.trello.rxlifecycle4.components.RxActivity
import com.trello.rxlifecycle4.components.support.RxFragment
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute

class AddReceiptPresenter(baseView: IAddReceiptView): BasePresenter<IAddReceiptView>(baseView) {

    fun addBill(bill: Bill) {
        val view = getView() ?: return
        val activity = view as RxActivity
        RetrofitManager.apiService.addBill(bill).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
            override fun onSuccess(model: HttpResult<Any?>) {

            }

        })
    }




}