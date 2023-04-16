package com.zzp.hhtally.ui.receipt

import com.trello.rxlifecycle4.components.support.RxFragment
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.showToast

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
                        getAllBills()
                    } else {
                        model.msg.showToast()
                    }
                }

            })
    }

    fun addLabel(labelName: String) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.addLabel(labelName).execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<Int>>() {
            override fun onSuccess(model: HttpResult<Int>) {
                if (model.code == 200) {
                    LabelUtil.labelList.add(Label(model.data, labelName, null))

                } else {
                    model.msg.showToast()
                }
            }

        })
    }

    fun removeLabel(labelId: Int) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.removeLabel(labelId).execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
            override fun onSuccess(model: HttpResult<Any?>) {

            }

        })
    }

    fun getAllBills() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getAllBills().execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
            override fun onSuccess(model: HttpResult<List<Bill>>) {
                if (model.code == 200) {
                    view.doRefreshSuccess(model.data)
                } else {
                    view.doRefreshError()
                    model.msg.showToast()
                }
            }

        })
    }

    fun getBillsByLabel(labelId: Int) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getBillsByLabel(labelId).execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
            override fun onSuccess(model: HttpResult<List<Bill>>) {

            }

        })
    }

    fun getBillsByDate(date: String) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getBillsByDate(date).execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
            override fun onSuccess(model: HttpResult<List<Bill>>) {

            }

        })
    }



}
