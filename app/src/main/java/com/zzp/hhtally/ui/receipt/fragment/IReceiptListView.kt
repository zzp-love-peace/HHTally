package com.zzp.hhtally.ui.receipt.fragment

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Bill

interface IReceiptListView : BaseView {
    fun doRefreshSuccess()

    fun doRefreshError()

}
