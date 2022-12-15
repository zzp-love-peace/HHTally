package com.zzp.hhtally.ui.main

import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.databinding.ActivityMainBinding
import com.zzp.hhtally.ui.chart.ChartFragment
import com.zzp.hhtally.ui.profile.ProfileFragment
import com.zzp.hhtally.ui.receipt.ReceiptFragment
import com.zzp.hhtally.util.logD

class MainActivity : BaseActivity<IMainView, MainPresenter>(), IMainView {

    private lateinit var binding: ActivityMainBinding

    override fun createPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {

    }

    override fun initView() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

}
