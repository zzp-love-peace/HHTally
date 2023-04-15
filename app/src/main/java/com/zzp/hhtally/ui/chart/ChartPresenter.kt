package com.zzp.hhtally.ui.chart

import android.graphics.Color
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.trello.rxlifecycle4.components.support.RxFragment
import com.zzp.hhtally.base.BasePresenter
import com.zzp.hhtally.data.chart.MonthInfo
import com.zzp.hhtally.data.chart.YearInfo
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute

class ChartPresenter(baseView: IChartView) : BasePresenter<IChartView>(baseView) {

    fun getYearInfo(year: String) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getYearInfo(year)
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<YearInfo>>() {
                override fun onSuccess(model: HttpResult<YearInfo>) {
                    val data = model.data
                    refreshExpenseChart(data.monthSum)
                }
            })
    }

    fun getMonthInfo(year: String, month: String) {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getMonthInfo(year, month)
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<MonthInfo>>() {
                override fun onSuccess(model: HttpResult<MonthInfo>) {
                }
            })
    }

    private fun getPieData(data: List<Double>): PieData {
        val visitors = mutableListOf<PieEntry>()
        data.forEachIndexed { index, d ->
            if (!d.equals(0.0)) {
                visitors.add(PieEntry(d.toFloat(), "${index}月"))
            }
        }

        val pieDataSet = PieDataSet(visitors, "Visitors").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        return PieData(pieDataSet)
    }

    private fun getBarData(data: List<Double>): BarData {
        val visitors = mutableListOf<BarEntry>()
        data.forEachIndexed { index, d ->
            visitors.add(BarEntry(index.toFloat(), d.toFloat()))
        }

        val barDataSet = BarDataSet(visitors, "Visitors").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        return BarData(barDataSet)
    }

    private fun getLineData(data: List<Double>): LineData {
        val visitors = mutableListOf<Entry>()
        data.forEachIndexed { index, d ->
            visitors.add(Entry(index.toFloat(), d.toFloat()))
        }

        val lineDataSet = LineDataSet(visitors, "Visitors")
        return LineData(lineDataSet)
    }

    fun refreshExpenseChart(data: List<Double>) {
        val view = getView() ?: return
        view.refreshChartData(getPieData(data), getBarData(data), getLineData(data))
    }

    fun refreshIncomeChart() {

    }
}
