package com.zzp.hhtally.ui.profile.report.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.databinding.FragmentReportBinding

class ReportChartFragment(private val type: Int) :
    BaseFragment<IReportChartView, ReportChartPresenter>(),
    IReportChartView {

    private lateinit var binding: FragmentReportBinding

    override fun createPresenter() = ReportChartPresenter(this)

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {
        @JvmStatic
        fun newExpenseInstance() =
            ReportChartFragment(TYPE_EXPENSE)

        @JvmStatic
        fun newIncomeInstance() =
            ReportChartFragment(TYPE_INCOME)
    }
}
