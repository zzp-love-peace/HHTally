package com.zzp.hhtally.ui.profile.report.fragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.databinding.FragmentReportBinding
import com.zzp.hhtally.util.getColorByAttr

class ReportChartFragment(private val billType: Int, private val dateType: Int) :
    BaseFragment<IReportChartView, ReportChartPresenter>(),
    IReportChartView {

    private lateinit var binding: FragmentReportBinding

    override fun createPresenter() = ReportChartPresenter(this)

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        presenter.billType = billType
        presenter.dateType = dateType
        presenter.refreshChart()
    }

    override fun initView() {
    }

    override fun refreshChartData(pieData: PieData, barData: BarData, lineData: LineData) {
        val colorOnSurface =
            requireContext().getColorByAttr(com.google.android.material.R.attr.colorOnSurface)
        binding.apply {
            pieChart.apply {
                data = pieData
                legend.isEnabled = false
                description.isEnabled = false
                animateY(800, Easing.EaseInOutQuad)
                // setExtraOffsets(24f, 0f, 24f, 0f)
                setEntryLabelColor(colorOnSurface)
                // setEntryLabelTextSize(11f)
                highlightValues(null)
                setUsePercentValues(true)
                setHoleColor(Color.TRANSPARENT)
                invalidate()
            }

            barChart.apply {
                data = barData
                legend.isEnabled = false
                description.isEnabled = false
                animateY(650, Easing.EaseInOutQuad)
                // setExtraOffsets(12f, 0f, 24f, 0f)
                setFitBars(true)
                setDrawBorders(false)
                setDrawGridBackground(false)
                highlightValues(null)
                invalidate()
            }

            lineChart.apply {
                data = lineData
                legend.isEnabled = false
                description.isEnabled = false
                animateY(800, Easing.EaseInOutQuad)
                setDrawBorders(false)
                setDrawGridBackground(false)
                highlightValues(null)
                invalidate()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(billType: Int, dateType: Int) =
            ReportChartFragment(billType, dateType)
    }
}
