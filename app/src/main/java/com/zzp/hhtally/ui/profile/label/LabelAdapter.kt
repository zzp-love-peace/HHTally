package com.zzp.hhtally.ui.profile.label

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zzp.hhtally.base.diffutil.BillDiffCalculator
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.databinding.ItemLabelBinding

class LabelAdapter(val onclick: (Label) -> Unit) : ListAdapter<Label, LabelAdapter.ViewHolder>(
    BillDiffCalculator.getCommonDiffItemCallback()
) {

    inner class ViewHolder(binding: ItemLabelBinding) : RecyclerView.ViewHolder(binding.root) {
        val label = binding.chipLabel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLabelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)

        binding.chipLabel.apply {
            setOnClickListener {
                val label = getItem(holder.adapterPosition)
                onclick(label)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val label = getItem(position)
        if (label.userId == 0) {
            holder.label.isCloseIconVisible = false
        }
        holder.label.text = label.labelName
    }
}
