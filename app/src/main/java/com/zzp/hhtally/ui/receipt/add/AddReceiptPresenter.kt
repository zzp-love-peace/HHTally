package com.zzp.hhtally.ui.receipt.add

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.network.textin.BillsCropData
import com.zzp.hhtally.network.textin.TextInResult
import com.zzp.hhtally.network.textin.TextInUtil
import com.zzp.hhtally.util.*
import kotlin.concurrent.thread


class AddReceiptPresenter(baseView: IAddReceiptView) : BasePresenter<IAddReceiptView>(baseView) {

    fun addBill(bill: Bill) {
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        RetrofitManager.apiService.addBill(bill)
            .execute(activity.bindToLifecycle(), object : HttpCallback<HttpResult<Any?>>() {
                override fun onSuccess(model: HttpResult<Any?>) {
                    if (model.code == 200) {
                        view.doAddSuccess()
                    }
                    model.msg.showToast()
                }

            })
    }

    fun analyzeImage(image: Bitmap?) {
        if (image == null) return
        val view = getView() ?: return
        val activity = view as RxAppCompatActivity
        val gson = Gson()
        thread {
            val type = object : TypeToken<TextInResult<BillsCropData>>() {}.type

            val result: TextInResult<BillsCropData> =
                gson.fromJson(TextInUtil.billsCrop(bitmap2ByteArray(image)), type)
            result.toString().logD()
            activity.runOnUiThread {
                if (result.code == 200) {
                    if (result.result.object_list.isNotEmpty()) {
                        var billPrice = ""
                        var billTime = ""
                        var billType = ""
                        var billShopkeeper = ""
                        result.result.object_list[0].apply {
                            billType = this.kind_description
                            when (this.type) {
                                "train_ticket" ->  {
                                    for(item in this.item_list) {
                                        when (item.key) {
                                            "price" -> billPrice = item.value
                                            "departure_date" -> billTime = item.value
                                        }
                                    }

                                }
                                "shop_receipt" -> {
                                    for(item in this.item_list) {
                                        when (item.key) {
                                            "money" -> billPrice = item.value
                                            "date" -> billTime = item.value
                                            "shop" -> billShopkeeper = item.value
                                        }
                                    }
                                }
                            }
                            view.refreshFromImg(billPrice, billTime, billType, billShopkeeper)
                        }
                    }
                } else {
                    result.message.showToast()
                }
            }
        }
    }


}