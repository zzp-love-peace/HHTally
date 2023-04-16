package com.zzp.hhtally.ui.profile.report

import com.google.android.material.tabs.TabLayoutMediator
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.databinding.ActivityReportBinding
import com.zzp.hhtally.ui.profile.report.fragment.ExInPageAdapter
import com.zzp.hhtally.ui.profile.report.fragment.ReportChartFragment

class ReportActivity : BaseActivity<IReportView, ReportPresenter>(),
    IReportView {

    private lateinit var binding: ActivityReportBinding

    private val fragmentList =
        listOf(
            ReportChartFragment.newExpenseInstance(),
            ReportChartFragment.newIncomeInstance()
        )

    override fun createPresenter() = ReportPresenter(this)

    override fun initViewBinding() {
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
    }

    override fun initView() {
        binding.viewPager2.adapter = ExInPageAdapter(this, fragmentList)

        val tabTitle = ArrayList<String>()
        tabTitle.add(resources.getString(R.string.expense))
        tabTitle.add(resources.getString(R.string.income))
        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}
