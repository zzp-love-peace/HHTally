package com.zzp.hhtally.ui.receipt.search

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.trello.rxlifecycle4.components.support.RxFragment
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TYPE_LABEL_SEARCH
import com.zzp.hhtally.data.TYPE_TIME_SEARCH
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.showToast

class SearchPresenter(baseView: ISearchView) : BasePresenter<ISearchView>(baseView) {


//    fun getBillsByLabel(labelId: Int) {
//        val view = getView() ?: return
//        val activity = view as RxAppCompatActivity
//        RetrofitManager.apiService.getBillsByLabel(labelId).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
//            override fun onSuccess(model: HttpResult<List<Bill>>) {
//                if (model.code == 200) {
//                    if (model.data.isNotEmpty()) view.doSearchSuccess(model.data.reversed(), TYPE_LABEL_SEARCH)
//                } else {
//                    model.msg.showToast()
//                }
//            }
//
//        })
//    }
//
//    fun getBillsByDate(date: String) {
//        val view = getView() ?: return
//        val activity = view as RxAppCompatActivity
//        RetrofitManager.apiService.getBillsByDate(date).execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
//            override fun onSuccess(model: HttpResult<List<Bill>>) {
//                if (model.code == 200) {
//                    if (model.data.isNotEmpty()) view.doSearchSuccess(model.data.reversed(), TYPE_TIME_SEARCH)
//                } else {
//                    model.msg.showToast()
//                }
//            }
//
//        })
//    }

    fun getBillsByCondition(pageNum: Int, startTime: String, endTime: String, labelId: Int?) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.getBillsByCondition(pageNum, startTime, endTime, labelId)
            .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<List<Bill>>>() {
                override fun onSuccess(model: HttpResult<List<Bill>>) {
                    if (model.code == 200) {
                        view.doSearchSuccess(model.data, pageNum == 1)
                    } else {
                        model.msg.showToast()
                    }
                }

            })
    }

}