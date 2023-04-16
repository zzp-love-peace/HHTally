package com.zzp.hhtally.ui.receipt.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.databinding.FragmentReceiptListBinding
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
        if (fragmentType == TYPE_EXPENSE) {
            billAdapter.submitList(UserData.expenseBillList)
        } else if (fragmentType == TYPE_INCOME) {
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
            ReceiptListFragment(TYPE_EXPENSE)

        @JvmStatic
        fun newIncomeInstance() =
            ReceiptListFragment(TYPE_INCOME)
    }
}
