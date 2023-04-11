package com.zzp.hhtally.ui.chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.databinding.FragmentChartBinding

class ChartFragment : BaseFragment<IChartView, ChartPresenter>(), IChartView {
    private lateinit var binding: FragmentChartBinding
    override fun createPresenter(): ChartPresenter {
        return ChartPresenter(this);
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {

    }

    override fun initView() {

    }


}
