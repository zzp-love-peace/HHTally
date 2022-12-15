package com.zzp.hhtally.base.diffutil

import androidx.recyclerview.widget.DiffUtil

class SimpleDiffItemCallback<T : Any>(
  private val areItemSame: (T, T) -> Boolean,
  private val areContentSame: (T, T) -> Boolean,
  private val changePayload: (T, T) -> Any? = { _: T, _: T -> null }
) : DiffUtil.ItemCallback<T>() {

  override fun getChangePayload(oldItem: T, newItem: T) = changePayload(oldItem, newItem)

  override fun areItemsTheSame(oldItem: T, newItem: T) = areItemSame(oldItem, newItem)

  override fun areContentsTheSame(oldItem: T, newItem: T) = areContentSame(oldItem, newItem)
}
