package com.zzp.hhtally.ui.profile.report.fragment

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
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.data.TYPE_MONTHLY
import com.zzp.hhtally.data.TYPE_WEEKLY
import com.zzp.hhtally.data.TYPE_YEARLY
import com.zzp.hhtally.data.chart.MonthInfo
import com.zzp.hhtally.data.chart.WeekInfo
import com.zzp.hhtally.data.chart.YearInfo
import com.zzp.hhtally.network.HttpCallback
import com.zzp.hhtally.network.HttpResult
import com.zzp.hhtally.network.RetrofitManager
import com.zzp.hhtally.util.execute
import com.zzp.hhtally.util.logD
import com.zzp.hhtally.util.showToast
import java.util.Calendar

class ReportChartPresenter(baseView: IReportChartView) :
    BasePresenter<IReportChartView>(baseView) {

    private var year = Calendar.getInstance().get(Calendar.YEAR).toString()
    private var month = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()
    var dateType = TYPE_MONTHLY
    var billType = TYPE_EXPENSE

    fun refreshChart() {
        when (billType) {
            TYPE_EXPENSE -> refreshExpenseChart()
            TYPE_INCOME -> refreshIncomeChart()
        }
    }

    private fun refreshExpenseChart() {
        when (dateType) {
            TYPE_YEARLY -> getYearInfo()
            TYPE_MONTHLY -> getMonthInfo()
            TYPE_WEEKLY -> getWeekInfo()
        }
    }

    private fun refreshIncomeChart() {
        when (dateType) {
            TYPE_YEARLY -> getYearIncome()
            TYPE_MONTHLY -> getMonthIncome()
            TYPE_WEEKLY -> getWeekIncome()
        }
    }

    private fun getYearInfo() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getYearInfo(year)
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<YearInfo>>() {
                override fun onSuccess(model: HttpResult<YearInfo>) {
                    if (model.code == 200) {
                        val data = model.data
                        data.toString().logD("yearExpense")
                        view.refreshChartData(
                            getPieData(data.monthSum),
                            getBarData(data.monthSum),
                            getLineData(data.monthSum),
                        )
                    } else {
                        model.msg.showToast()
                    }
                }
            })
    }

    private fun getMonthInfo() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        month = if (month.length == 1) "0$month" else month
        "$year//$month".logD("month")
        RetrofitManager.apiService.getMonthInfo(year, month)
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<MonthInfo>>() {
                override fun onSuccess(model: HttpResult<MonthInfo>) {
                    if (model.code == 200) {
                        val data = model.data
                        model.code.toString().logD("month")
                        // data.toString().logD("monthExpense")
                        view.refreshChartData(
                            getPieData(data.everyday),
                            getBarData(data.everyday),
                            getLineData(data.everyday),
                        )
                    } else {
                        model.msg.showToast()
                    }
                }
            })
    }

    private fun getWeekInfo() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getWeekInfo()
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<WeekInfo>>() {
                override fun onSuccess(model: HttpResult<WeekInfo>) {
                    if (model.code == 200) {
                        val data = model.data
                        data.everyday.toString().logD("weekExpense")
                        view.refreshChartData(
                            getPieData(data.everyday),
                            getBarData(data.everyday),
                            getLineData(data.everyday),
                        )
                    } else {
                        model.msg.showToast()
                    }
                }
            })
    }

    private fun getYearIncome() {
        val view = getView() ?: return
        val fragment = view as RxFragment
        RetrofitManager.apiService.getYearIncome(year)
            .execute(fragment.bindToLifecycle(), object : HttpCallback<HttpResult<YearInfo>>() {
                override fun onSuccess(model: HttpResult<YearInfo>) {
                    if (model.code == 200) {
                        val data = model.data
                        data.toString().logD("yearIncome")
                        view.refreshChartData(
                            getPieData(data.monthSum),
                            getBarData(data.monthSum),
                            getLineData(data.monthSum),
                        )
                    } else {
                        model.msg.showToast()
                    }
                }
            })
    }

    private fun getMonthIncome() {
        // TODO
        // 调用月收入接口
    }

    private fun getWeekIncome() {
        // TODO
        // 调用近一周收入接口
    }

    private fun getPieData(data: List<Double>): PieData {
        val visitors = mutableListOf<PieEntry>()
        data.toString().logD("pie")
        data.forEachIndexed { index, d ->
            if (!d.equals(0.0)) {
                when (dateType) {
                    TYPE_MONTHLY ->
                        visitors.add(PieEntry(d.toFloat(), "${index}日"))

                    TYPE_YEARLY ->
                        visitors.add(PieEntry(d.toFloat(), "${index}月"))
                }
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
            // valueTextSize = 16f
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
}
