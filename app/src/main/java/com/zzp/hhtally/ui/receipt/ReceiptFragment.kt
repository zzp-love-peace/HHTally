package com.zzp.hhtally.ui.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.databinding.FragmentReceiptBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter

 class ReceiptFragment : BaseFragment<IReceiptView, ReceiptPresenter>(), IReceiptView {

     private val billAdapter = BillAdapter(listOf())
     private lateinit var binding: FragmentReceiptBinding

             override fun createPresenter(): ReceiptPresenter {
         return ReceiptPresenter(this)
     }

     override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
     }

     override fun initData() {

     }

     override fun initView() {

     }
 }

