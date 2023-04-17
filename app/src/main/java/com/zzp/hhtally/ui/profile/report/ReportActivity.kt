package com.zzp.hhtally.ui.profile.report

import com.google.android.material.tabs.TabLayoutMediator
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.data.TYPE_MONTHLY
import com.zzp.hhtally.databinding.ActivityReportBinding
import com.zzp.hhtally.ui.profile.report.fragment.ExInPageAdapter
import com.zzp.hhtally.ui.profile.report.fragment.ReportChartFragment

class ReportActivity : BaseActivity<IReportView, ReportPresenter>(),
    IReportView {

    private lateinit var binding: ActivityReportBinding

    private val dateType by lazy {
        intent.getIntExtra("date_type", TYPE_MONTHLY)
    }

    private val fragmentList by lazy {
        listOf(
            ReportChartFragment.newInstance(
                TYPE_EXPENSE,
                dateType
            ),
            ReportChartFragment.newInstance(
                TYPE_INCOME,
                dateType
            ),
        )
    }

    override fun createPresenter() = ReportPresenter(this)

    override fun initViewBinding() {
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
    }

    override fun initView() {
        binding.toolbar.apply {
            title = if (dateType == TYPE_MONTHLY) {
                resources.getString(R.string.monthly_report)
            } else {
                resources.getString(R.string.yearly_report)
            }
            setNavigationOnClickListener {
                finish()
            }
        }

        binding.viewPager2.adapter = ExInPageAdapter(this, fragmentList)

        val tabTitle = ArrayList<String>()
        tabTitle.add(resources.getString(R.string.expense))
        tabTitle.add(resources.getString(R.string.income))
        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }
}
