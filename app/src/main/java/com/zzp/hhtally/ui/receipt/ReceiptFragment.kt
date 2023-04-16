package com.zzp.hhtally.ui.receipt

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.databinding.FragmentReceiptBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import com.zzp.hhtally.ui.receipt.adapter.ViewPagerAdapter
import com.zzp.hhtally.ui.receipt.add.AddReceiptActivity
import com.zzp.hhtally.ui.receipt.fragment.ReceiptListFragment
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.showToast


class ReceiptFragment : BaseFragment<IReceiptView, ReceiptPresenter>(), IReceiptView {

    private lateinit var binding: FragmentReceiptBinding

    private lateinit var myActivityLauncher: ActivityResultLauncher<Intent>

    private val expenseFragment = ReceiptListFragment.newExpenseInstance()
    private val incomeFragment = ReceiptListFragment.newIncomeInstance()

    override fun createPresenter(): ReceiptPresenter {
        return ReceiptPresenter(this)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        myActivityLauncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
            if(activityResult.resultCode == Activity.RESULT_OK){
                val result = activityResult.data?.getIntExtra("result", -1)
                if (result == 0) {
                    presenter.getAllBills()
                }
            }
        }
        if (LabelUtil.labelList.isEmpty()) presenter.getAllLabels()
    }

    override fun initView() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        val data = ArrayList<Fragment>()
        data.add(expenseFragment)
        data.add(incomeFragment)
        val tabTitle = ArrayList<String>()
        tabTitle.add("支出")
        tabTitle.add("收入")
        val viewPagerAdapter = ViewPagerAdapter(requireActivity(), data)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){ tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.receipt_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(requireContext(), AddReceiptActivity::class.java)
                myActivityLauncher.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun doRefreshSuccess(expenseBillList: List<Bill>, incomeBillList: List<Bill>) {
        expenseFragment.doRefreshSuccess()
        incomeFragment.doRefreshSuccess()

    }

    override fun doRefreshError() {
        expenseFragment.doRefreshError()
        incomeFragment.doRefreshError()


    }
}

