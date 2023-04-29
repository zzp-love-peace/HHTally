package com.zzp.hhtally.ui.receipt.info

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragmentActivity
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.showToast


class ReceiptInfoPresenter(baseView: IReceiptInfoView) : BasePresenter<IReceiptInfoView>(baseView) {

    fun removeBill(billId: Int) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.removeBill(billId).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
            override fun onSuccess(model: HttpResult<Any?>) {
                if (model.code == 200) {
                    view.doRemoveSuccess()
                }
                model.msg.showToast()
            }

        })
    }

    fun updateBill(bill: Bill) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.updateBill(bill).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
            override fun onSuccess(model: HttpResult<Any?>) {
                if (model.code == 200) {
                    view.doUpdateSuccess()
                }
                model.msg.showToast()
            }

        })
    }

}