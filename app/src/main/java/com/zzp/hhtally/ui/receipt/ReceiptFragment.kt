package com.zzp.hhtally.ui.receipt

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.databinding.FragmentReceiptBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import com.zzp.hhtally.ui.receipt.add.AddReceiptActivity


class ReceiptFragment : BaseFragment<IReceiptView, ReceiptPresenter>(), IReceiptView {

    private val billAdapter = BillAdapter()


    private lateinit var binding: FragmentReceiptBinding

    override fun createPresenter(): ReceiptPresenter {
        return ReceiptPresenter(this)
    }

    override fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initData() {
        presenter.getAllLabels()
    }

    override fun initView() {
        binding.rvBill.adapter = billAdapter
        binding.fabAddReceipt.setOnClickListener {
            val intent = Intent(requireContext(), AddReceiptActivity::class.java)
            startActivity(intent)
        }
    }

    override fun doRefreshSuccess(newBillList: List<Bill>) {
        binding.rvBill.visibility = View.VISIBLE
        binding.loadingContainer.root.visibility = View.GONE
        Log.d(TAG, "doRefreshSuccess: ${newBillList.size}")
        billAdapter.submitList(newBillList)
    }

    override fun doRefreshError() {
        binding.rvBill.visibility = View.GONE
        binding.loadingContainer.root.visibility = View.VISIBLE
    }
}

