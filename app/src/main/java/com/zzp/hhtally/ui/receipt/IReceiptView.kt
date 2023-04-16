package com.zzp.hhtally.ui.receipt

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Bill

interface IReceiptView : BaseView {
    fun doRefreshSuccess(expenseBillList: List<Bill>, incomeBillList: List<Bill>)

    fun doRefreshError()

}
