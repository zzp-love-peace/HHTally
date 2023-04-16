package com.zzp.hhtally.ui.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.PieData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.*
import com.zzp.hhtally.databinding.FragmentChartBinding
import com.zzp.hhtally.util.getColorByAttr

class ChartFragment : BaseFragment<IChartView, ChartPresenter>(), IChartView {
    private lateinit var binding: FragmentChartBinding

    private val datePickerDialog by lazy {
        DatePickerDialog(
            requireContext(),
            { year, month ->
                binding.topAppBar.title =
                    "${year}年/${month}月"

                presenter.year = year
                presenter.month = month
                presenter.dateType = TYPE_MONTHLY
                presenter.refreshChart()
            },
            { year ->
                binding.topAppBar.title =
                    "${year}全年账单"

                presenter.year = year
                presenter.dateType = TYPE_YEARLY
                presenter.refreshChart()
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
        presenter.refreshChart()
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
                R.id.expense -> {
                    if (isChecked) {
                        presenter.billType = TYPE_EXPENSE
                        presenter.refreshChart()
                    }
                }

                R.id.income -> {
                    if (isChecked) {
                        presenter.billType = TYPE_INCOME
                        presenter.refreshChart()
                    }
                }
            }
        }
    }

    private fun initTopAppBar() {
        binding.topAppBar.title = "${presenter.year}年/${presenter.month}月"
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.recentWeek -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.content_description_recent_week)
                        .setPositiveButton("确定") { _, _ ->
                            binding.topAppBar.title = resources.getString(R.string.recent_week)

                            presenter.dateType = TYPE_WEEKLY
                            presenter.refreshChart()
                        }
                        .setNegativeButton("取消") { _, _ -> }
                        .create()
                        .show()
                    return@setOnMenuItemClickListener true
                }

                R.id.dataRange -> {
                    datePickerDialog.show()
                    return@setOnMenuItemClickListener true
                }

                else -> false
            }
        }
    }

}
