package com.zzp.hhtally.ui.receipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zzp.hhtally.base.diffutil.BillDiffCalculator
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.databinding.ItemBillBinding

class BillAdapter(private val items: List<Bill>)
    : ListAdapter<Bill, BillAdapter.ViewHolder>(BillDiffCalculator.getCommonDiffItemCallback()) {

    inner class ViewHolder(binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        val billLabel = binding.tvBillLabel
        val billMoney = binding.tvBillMoney
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = items[position]
        holder.billLabel.text = bill.label
        holder.billMoney.text = bill.money.toString()
    }
}
