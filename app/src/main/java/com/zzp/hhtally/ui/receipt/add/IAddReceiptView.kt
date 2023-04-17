package com.zzp.hhtally.ui.receipt.add

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Label

interface IAddReceiptView: BaseView {
    fun doAddSuccess()

    fun refreshFromImg(price: String, time: String, type: String, shopkeeper: String)

    fun showLoadingDialog()

    fun dismissLoadingDialog()

    fun refreshLabelPicker(labelList: List<Label>)

}