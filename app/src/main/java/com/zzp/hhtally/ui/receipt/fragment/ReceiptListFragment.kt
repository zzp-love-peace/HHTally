package com.zzp.hhtally.ui.receipt.fragment


import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.databinding.FragmentReceiptListBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter

class ReceiptListFragment(private val fragmentType: Int) : BaseFragment<IReceiptListView, ReceiptListPresenter>(),
    IReceiptListView {
    
    private lateinit var binding: FragmentReceiptListBinding
    private lateinit var billAdapter : BillAdapter
    private lateinit var removeReceiptActivityLauncher: ActivityResultLauncher<Intent>
    override fun createPresenter()= ReceiptListPresenter(this)

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        removeReceiptActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
            if(activityResult.resultCode == Activity.RESULT_OK){
                presenter.getAllBills()
            }
        }
        billAdapter = BillAdapter(requireContext(), removeReceiptActivityLauncher)
        presenter.getAllBills()
    }

    override fun initView() {
        binding.rvBill.adapter = billAdapter
    }

    override fun doRefreshSuccess() {
        binding.rvBill.visibility = View.VISIBLE
        binding.loadingContainer.root.visibility = View.GONE
        if (fragmentType == TYPE_EXPENSE) {
            billAdapter.submitList(UserData.expenseBillList.toList())
        } else if (fragmentType == TYPE_INCOME) {
            billAdapter.submitList(UserData.incomeBillList.toList())
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
