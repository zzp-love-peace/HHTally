package com.zzp.hhtally.ui.profile.label

import com.zzp.hhtally.base.BaseView
import com.zzp.hhtally.data.Label

interface ILabelView : BaseView {

    fun refreshLabels(labelList: List<Label>)

    fun doRefreshSuccess()

    fun doRefreshError()
}
