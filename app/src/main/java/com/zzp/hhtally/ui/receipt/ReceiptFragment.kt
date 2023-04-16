package com.zzp.hhtally.ui.receipt

import android.content.Intent
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.databinding.FragmentReceiptBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import com.zzp.hhtally.ui.receipt.add.AddReceiptActivity
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.showToast


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
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)
        binding.rvBill.adapter = billAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.receipt_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(requireContext(), AddReceiptActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

