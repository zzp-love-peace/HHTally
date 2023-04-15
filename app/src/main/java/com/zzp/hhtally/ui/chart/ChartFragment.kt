package com.zzp.hhtally.ui.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.databinding.FragmentChartBinding
import com.zzp.hhtally.util.getColorByAttr
import java.util.Calendar


class ChartFragment : BaseFragment<IChartView, ChartPresenter>(), IChartView {
    private lateinit var binding: FragmentChartBinding

    private var year = Calendar.getInstance().get(Calendar.YEAR).toString()
    private var month = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()

    private val datePickerDialog by lazy {
        DatePickerDialog(
            requireContext(),
            { year, month ->
                this.year = year
                this.month = month
                binding.topAppBar.title =
                    "${year}年/${month}月"

                presenter.getMonthInfo(year, month)
            },
            { year ->
                this.year = year
                binding.topAppBar.title =
                    "${year}全年账单"

                presenter.getYearInfo(year)
            }
        )
    }

    override fun createPresenter(): ChartPresenter {
        return ChartPresenter(this)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        // presenter.refreshExpenseChart()
    }

    override fun initView() {
        initToggleButton()
        initTopAppBar()
    }

    override fun refreshChartData(pieData: PieData, barData: BarData, lineData: LineData) {
        val colorOnSurface =
            requireContext().getColorByAttr(com.google.android.material.R.attr.colorOnSurface)
        binding.containerChart.apply {
            pieChart.apply {
                data = pieData
                legend.isEnabled = false
                animateY(800, Easing.EaseInOutQuad)
                setExtraOffsets(24f, 0f, 24f, 0f)
                setEntryLabelColor(colorOnSurface)
                setEntryLabelTextSize(11f)
                description.isEnabled = false
                highlightValues(null)
                setUsePercentValues(true)
                setHoleColor(Color.TRANSPARENT)
                invalidate()
            }

            barChart.apply {
                data = barData
                description.isEnabled = false
                legend.isEnabled = false
                animateY(650, Easing.EaseInOutQuad)
                setExtraOffsets(12f, 0f, 24f, 0f)
                setFitBars(true)
                setDrawBorders(false)
                setDrawGridBackground(false)
                highlightValues(null)
                invalidate()
            }

            lineChart.apply {
                data = lineData
                description.isEnabled = false
                animateY(800, Easing.EaseInOutQuad)
                setDrawBorders(false)
                setDrawGridBackground(false)
                highlightValues(null)
                invalidate()
            }
        }
    }

    private fun initToggleButton() {
        binding.containerChart.toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when (checkedId) {
                com.zzp.hhtally.R.id.expense -> {
                    if (isChecked) {
                        presenter.getYearInfo(year)
                    }
                }

                com.zzp.hhtally.R.id.income -> {
                    if (isChecked) {
                        presenter.getYearInfo(year)
                    }
                }
            }
        }
    }

    private fun initTopAppBar() {
        binding.topAppBar.title = "${year}年/${month}月"
        binding.topAppBar.setOnClickListener {
            datePickerDialog.show()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                com.zzp.hhtally.R.id.data_range -> {
                    return@setOnMenuItemClickListener true
                }

                else -> false
            }
        }
    }

}
