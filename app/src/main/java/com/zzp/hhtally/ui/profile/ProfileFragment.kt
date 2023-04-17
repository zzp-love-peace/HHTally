package com.zzp.hhtally.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.TYPE_MONTHLY
import com.zzp.hhtally.data.TYPE_YEARLY
import com.zzp.hhtally.databinding.FragmentProfileBinding
import com.zzp.hhtally.ui.profile.report.ReportActivity
import com.zzp.hhtally.util.showToast

class ProfileFragment : BaseFragment<IProfileView, ProfilePresenter>(), IProfileView {
    private lateinit var binding: FragmentProfileBinding

    private val prefs by lazy {
        requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    }

    override fun createPresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {

    }

    override fun initView() {
        initSettingsNav()
    }

    private fun initSettingsNav() {
        val username = prefs.getString("username", "")
        binding.navSettings
            .getHeaderView(0)
            .findViewById<TextView>(R.id.username).text = username
        binding.navSettings.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settings_label -> {
                    // todo
                    "跳转label页".showToast()
                }

                R.id.settings_yearly_report -> {
                    val intent = Intent(requireActivity(), ReportActivity::class.java)
                    intent.putExtra("date_type", TYPE_YEARLY)
                    startActivity(intent)
                }

                R.id.settings_monthly_report -> {
                    val intent = Intent(requireActivity(), ReportActivity::class.java)
                    intent.putExtra("date_type", TYPE_MONTHLY)
                    startActivity(intent)
                }
            }
            true
        }
    }

}
