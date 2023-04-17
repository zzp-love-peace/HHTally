package com.zzp.hhtally.ui.profile.report.fragment

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.zzp.hhtally.base.BaseView

interface IReportChartView : BaseView {
    fun refreshChartData(pieData: PieData, barData: BarData, lineData: LineData)
}
