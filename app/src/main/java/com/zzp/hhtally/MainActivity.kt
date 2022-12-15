package com.zzp.hhtally

import android.view.MenuItem
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.databinding.ActivityMainBinding
import com.zzp.hhtally.ui.chart.ChartFragment
import com.zzp.hhtally.ui.profile.ProfileFragment
import com.zzp.hhtally.ui.receipt.ReceiptFragment
import com.zzp.hhtally.util.logD

class MainActivity : BaseActivity<IMainView, MainPresenter>(), IMainView {

    private lateinit var binding: ActivityMainBinding

    private var fragmentList = listOf(
        ChartFragment(),
        ReceiptFragment(),
        ProfileFragment()
    )
    private var activeFragmentIndex = -1

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
        binding.apply {
            navView.setOnItemSelectedListener(
                NavBottomViewDoubleClickListener(
                    this@MainActivity::onBottomItemSelect,
                    this@MainActivity::onBottomDoubleClick
                )
            )
            navView.selectedItemId = R.id.navigation_receipt
        }
        switchFragment(1)
    }

    override fun switchFragment(fragmentIndex: Int) {
        "进了${fragmentIndex}".logD(TAG)
        if (fragmentIndex != activeFragmentIndex) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = fragmentList[fragmentIndex]
            fragmentList.getOrNull(activeFragmentIndex)?.apply(fragmentTransaction::hide)
            if (fragment.isAdded) {
                fragmentTransaction.show(fragment)
            } else {
                fragmentTransaction
                    .add(
                        R.id.nav_host_fragment_container,
                        fragment,
                        fragment.javaClass.simpleName
                    )
                    .show(fragment)
            }
            fragmentTransaction.commitAllowingStateLoss()
            activeFragmentIndex = fragmentIndex
        }
    }

    override fun onBottomItemSelect(item: MenuItem): Boolean {
        switchFragment(getFragmentIndexFromItemId(item.itemId))
        return true
    }

    override fun onBottomDoubleClick(item: MenuItem) {
        // 可以双击返回顶部
    }

    private fun getFragmentIndexFromItemId(itemId: Int): Int {
        return when (itemId) {
            R.id.navigation_chart -> 0
            R.id.navigation_receipt -> 1
            R.id.navigation_profile -> 2
            else -> 1
        }
    }
}
