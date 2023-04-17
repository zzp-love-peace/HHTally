package com.zzp.hhtally.ui.profile.label

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.logD
import com.zzp.hhtally.util.showToast

class LabelPresenter(baseView: ILabelView) : BasePresenter<ILabelView>(baseView) {

    fun getAllLabels() {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.getAllLabels()
            .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<List<Label>>>() {
                override fun onSuccess(model: HttpResult<List<Label>>) {
                    if (model.code == 200) {
                        val labelList = model.data
                        view.refreshLabels(labelList)
                        view.doRefreshSuccess()
                    } else {
                        view.doRefreshError()
                        model.msg.showToast()
                    }
                }

            })
    }

    fun addLabel(labelName: String) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.addLabel(labelName)
            .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Int>>() {
                override fun onSuccess(model: HttpResult<Int>) {
                    if (model.code == 200) {
                        labelName.logD("success")
                        getAllLabels()
                    } else {
                        labelName.logD("fail")
                        model.msg.showToast()
                    }
                }

            })
    }

    fun removeLabel(label: Label) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        if (label.userId == 0) {
            "无法删除默认标签".showToast()
        } else {
            RetrofitManager.apiService.removeLabel(label.labelId)
                .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
                    override fun onSuccess(model: HttpResult<Any?>) {
                        if (model.code == 200) {
                            getAllLabels()
                        } else {
                            model.msg.showToast()
                        }
                    }
                })
        }
    }
}
