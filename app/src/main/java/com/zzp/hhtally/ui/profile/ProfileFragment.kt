package com.zzp.hhtally.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<IProfileView, ProfilePresenter>(), IProfileView {
    private lateinit var binding: FragmentProfileBinding
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

    }


}
