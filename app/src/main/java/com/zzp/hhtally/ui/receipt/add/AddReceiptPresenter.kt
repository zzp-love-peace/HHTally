package com.zzp.hhtally.ui.receipt.add

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
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
        view.showLoadingDialog()
        thread {
            val type = object : TypeToken<TextInResult<BillsCropData>>() {}.type

            val result: TextInResult<BillsCropData> =
                gson.fromJson(TextInUtil.billsCrop(bitmap2ByteArray(image)), type)
//            result.toString().logD()
            activity.runOnUiThread {
                if (result.code == 200) {
                    view.dismissLoadingDialog()
                    if (result.result.object_list.isNotEmpty()) {
                        var billPrice = ""
                        var billTime = ""
                        var billType = ""
                        var billShopkeeper = ""
                        result.result.object_list[0].apply {
                            billType = this.kind_description

                            if (!LabelUtil.isContainLabelName(billType)) {
                                RetrofitManager.apiService.addLabel(billType).execute(
                                    activity.bindToLifecycle(),
                                    object : HttpCallback<HttpResult<Int>>() {
                                        override fun onSuccess(model: HttpResult<Int>) {
                                            if (model.code == 200) {
                                                LabelUtil.labelList.add(
                                                    Label(
                                                        -1,
                                                        model.data,
                                                        billType,
                                                        null
                                                    )
                                                )
                                                view.refreshLabelPicker(LabelUtil.labelList)
                                            } else {
                                                model.msg.showToast()
                                                billType = ""
                                            }
                                        }

                                    })
                            }

                            when (this.type) {
                                //火车票
                                "train_ticket" -> {
                                    for (item in this.item_list) {
                                        when (item.key) {
                                            "price" -> billPrice = item.value
                                            "departure_date" -> billTime = item.value
                                        }
                                    }

                                }
                                //商户小票
                                "shop_receipt" -> {
                                    for (item in this.item_list) {
                                        when (item.key) {
                                            "money" -> billPrice = item.value
                                            "date" -> billTime = item.value
                                            "shop" -> billShopkeeper = item.value
                                        }
                                    }
                                }
                                //医疗费收据
                                "medical_receipt" -> {
                                    for (item in this.item_list) {
                                        when (item.key) {
                                            "amount_small" -> billPrice = item.value
                                        }
                                    }
                                }
                                //出租车发票
                                "taxi_ticket" -> {
                                    for (item in this.item_list) {
                                        when (item.key) {
                                            "sum" -> billPrice = item.value
                                            "date" -> billTime = item.value
                                        }
                                    }
                                }
                                //增值税普通发票
                                "vat_special_invoice", "vat_electronic_special_invoice", "vat_electronic_invoice", "vat_common_invoice", "vat_electronic_toll_invoice", "machine_printed_invoice", "blockchain_electronic_invoice", "vat_electronic_invoice_new", "vat_electronic_special_invoice_new" -> {
                                    for (item in this.item_list) {
                                        when (item.key) {
                                            "vat_invoice_price_list" -> billPrice = item.value
                                            "vat_invoice_issue_date" -> billTime = item.value
                                            "vat_invoice_seller_name" -> billShopkeeper = item.value
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