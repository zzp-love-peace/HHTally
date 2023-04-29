package com.zzp.hhtally.ui.receipt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zzp.hhtally.R
import com.zzp.hhtally.base.diffutil.BillDiffCalculator
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.data.TYPE_INCOME
import com.zzp.hhtally.databinding.ItemBillBinding
import com.zzp.hhtally.ui.receipt.info.ReceiptInfoActivity
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.logD
import kotlin.math.abs

class BillAdapter(private val context :Context, private val launcher: ActivityResultLauncher<Intent>)
    : ListAdapter<Bill, BillAdapter.ViewHolder>(BillDiffCalculator.getCommonDiffItemCallback()) {

    inner class ViewHolder(binding: ItemBillBinding) : RecyclerView.ViewHolder(binding.root) {
        val billLabel = binding.tvBillLabel
        val billMoney = binding.tvBillMoney
        val billTime = binding.tvBillTime
        val billShoKeeper = binding.tvBillShopkeeper
        val billImage = binding.imageBill
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        binding.root.setOnClickListener {
            val bill = getItem(holder.adapterPosition)
            val intent = Intent(context, ReceiptInfoActivity::class.java)
            intent.putExtra("bill", bill)
            intent.putExtra("type", if (bill.money > 0) TYPE_EXPENSE else TYPE_INCOME)
            launcher.launch(intent)
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = getItem(position)
        holder.billLabel.text = LabelUtil.getLabelNameFormId(bill.labelId)
        holder.billMoney.text = abs(bill.money).toString()
        holder.billTime.text = bill.time.split(" ")[0]
        holder.billShoKeeper.text = bill.shopkeeper
        holder.billImage.setImageResource(if (bill.money > 0) R.drawable.ic_expense else R.drawable.ic_income)
    }
}
