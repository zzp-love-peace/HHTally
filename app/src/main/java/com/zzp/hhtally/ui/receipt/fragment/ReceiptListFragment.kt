package com.zzp.hhtally.ui.receipt.fragment


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.databinding.FragmentReceiptListBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import kotlin.math.log

class ReceiptListFragment(private val fragmentType: Int) :
    BaseFragment<IReceiptListView, ReceiptListPresenter>(),
    IReceiptListView {

    private lateinit var binding: FragmentReceiptListBinding
    private lateinit var billAdapter: BillAdapter
    private lateinit var receiptInfoActivityLauncher: ActivityResultLauncher<Intent>

    private var pageNum = 1

    override fun createPresenter() = ReceiptListPresenter(this)

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initData() {
        receiptInfoActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val isRemove = activityResult.data?.getBooleanExtra("isRemove", true)
                    if (isRemove != null) {
                        val list = arrayListOf<Bill>()
                        if (isRemove) {
                            val id = activityResult.data?.getIntExtra("billId", -1)
                            if (id != -1) {
                                list.addAll(billAdapter.currentList)
                                list.removeIf {
                                    it.billId == id
                                }
                            }
                        } else {
                            val bill = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                activityResult.data?.getSerializableExtra(
                                    "bill",
                                    Bill::class.java
                                )!!
                            } else {
                                activityResult.data?.getSerializableExtra("bill") as Bill
                            }

                            list.addAll(billAdapter.currentList)

                            for(i in 0..list.size) {
                                if (list[i].billId == bill.billId) {
                                    list.removeAt(i)
                                    list.add(i, bill)
                                    break
                                }
                            }
                        }
                        billAdapter.submitList(list)
                    }
                }
            }
        billAdapter = BillAdapter(requireContext(), receiptInfoActivityLauncher)
        presenter.getAllBills(pageNum, fragmentType)
    }

    override fun initView() {
        binding.rvBill.adapter = billAdapter
        binding.layoutRefresh.setEnableLoadMoreWhenContentNotFull(false)
        binding.layoutRefresh.setOnRefreshListener {
            pageNum = 1
            presenter.getAllBills(pageNum, fragmentType)
        }

        binding.layoutRefresh.setOnLoadMoreListener {
            pageNum++
            presenter.getAllBills(pageNum, fragmentType)
        }

    }

    override fun doRefreshSuccess(data: List<Bill>, isFirst: Boolean) {
        binding.rvBill.visibility = View.VISIBLE
        binding.loadingContainer.root.visibility = View.GONE
        if (isFirst) {
            billAdapter.submitList(data)
        } else {
            val list = arrayListOf<Bill>()
            list.addAll(billAdapter.currentList)
            list.addAll(data)
            billAdapter.submitList(list)
        }
        if (binding.layoutRefresh.isRefreshing) {
            binding.layoutRefresh.finishRefresh()
        }
        if (binding.layoutRefresh.isLoading) {
            binding.layoutRefresh.finishLoadMore()
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
