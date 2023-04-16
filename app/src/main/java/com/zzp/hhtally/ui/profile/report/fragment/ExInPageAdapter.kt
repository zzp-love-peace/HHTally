package com.zzp.hhtally.ui.profile.report.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ExInPageAdapter(activity: FragmentActivity, private val fragmentList: List<Fragment>) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}
