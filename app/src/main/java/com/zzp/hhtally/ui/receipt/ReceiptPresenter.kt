package com.zzp.hhtally.ui.receipt

import com.trello.rxlifecycle4.components.support.RxFragment
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.showToast
import kotlin.random.Random

class ReceiptPresenter(baseView: IReceiptView) : BasePresenter<IReceiptView>(baseView) {

    fun getAllLabels() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getAllLabels()
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<List<Label>>>() {
                override fun onSuccess(model: HttpResult<List<Label>>) {
                    if (model.code == 200) {
                        LabelUtil.labelList.clear()
                        LabelUtil.labelList.addAll(model.data)
//                        if (UserData.billList.isEmpty()) getAllBills()
                    } else {
                        model.msg.showToast()
                    }
                }

            })
    }

    fun getAllBills(type: Int) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getAllBills(1, type).execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
            override fun onSuccess(model: HttpResult<List<Bill>>) {
                if (model.code == 200) {
                    val billList = model.data
                    view.doRefreshSuccess(billList, type)
                } else {
                    view.doRefreshError()
                    model.msg.showToast()
                }
            }

        })
    }
}
