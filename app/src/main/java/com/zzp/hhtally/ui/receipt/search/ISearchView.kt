package com.zzp.hhtally.ui.receipt.search

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Bill

interface ISearchView: BaseView {
    fun doSearchSuccess(billList: List<Bill>, type: Int)
}