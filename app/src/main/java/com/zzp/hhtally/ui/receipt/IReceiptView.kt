package com.zzp.hhtally.ui.receipt

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Bill

interface IReceiptView : BaseView {
    fun doRefreshSuccess(newBillList: List<Bill>)

    fun doRefreshError()

}
