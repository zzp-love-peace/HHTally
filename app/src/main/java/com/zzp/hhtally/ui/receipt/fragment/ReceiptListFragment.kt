package com.zzp.hhtally.ui.receipt.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.FRAGMENT_EXPENSE
import com.zzp.hhtally.data.FRAGMENT_INCOME
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.databinding.FragmentReceiptListBinding
import com.zzp.hhtally.ui.receipt.IReceiptView
import com.zzp.hhtally.ui.receipt.ReceiptPresenter
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter

class ReceiptListFragment(private val fragmentType: Int) : BaseFragment<IReceiptListView, ReceiptListPresenter>(),
    IReceiptListView {
    
    private lateinit var binding: FragmentReceiptListBinding
    private val billAdapter = BillAdapter()
    override fun createPresenter()= ReceiptListPresenter(this)

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        presenter.getAllBills()
    }

    override fun initView() {
        binding.rvBill.adapter = billAdapter
    }

    override fun doRefreshSuccess() {
        binding.rvBill.visibility = View.VISIBLE
        binding.loadingContainer.root.visibility = View.GONE
        if (fragmentType == FRAGMENT_EXPENSE) {
            billAdapter.submitList(UserData.expenseBillList)
        } else if (fragmentType == FRAGMENT_INCOME) {
            billAdapter.submitList(UserData.incomeBillList)
        }
    }

    override fun doRefreshError() {
        binding.rvBill.visibility = View.GONE
        binding.loadingContainer.root.visibility = View.VISIBLE
    }
    
    companion object {
        @JvmStatic
        fun newExpenseInstance() =
            ReceiptListFragment(FRAGMENT_EXPENSE)

        @JvmStatic
        fun newIncomeInstance() =
            ReceiptListFragment(FRAGMENT_INCOME)
    }
}