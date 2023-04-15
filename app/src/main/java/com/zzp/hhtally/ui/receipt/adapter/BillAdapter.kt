package com.zzp.hhtally.ui.receipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zzp.hhtally.base.diffutil.BillDiffCalculator
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.databinding.ItemBillBinding
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.logD

class BillAdapter()
    : ListAdapter<Bill, BillAdapter.ViewHolder>(BillDiffCalculator.getCommonDiffItemCallback()) {

    inner class ViewHolder(binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        val billLabel = binding.tvBillLabel
        val billMoney = binding.tvBillMoney
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        binding.root.setOnClickListener {
            "点了${holder.adapterPosition}".logD()
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = getItem(position)
        holder.billLabel.text = LabelUtil.getLabelNameFormId(bill.labelId)
        holder.billMoney.text = bill.money.toString()
        "onBindViewHolder".logD()
    }
}
