package com.zzp.hhtally.base.diffutil

import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.User

object BillDiffCalculator {

    fun getCommonDiffCallback(oldList: List<Any>, newList: List<Any>) =
        SimpleDiffCallback(
            oldList,
            newList,
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is User && newItem is User -> oldItem.username == newItem.username
                    oldItem is Bill && newItem is Bill -> oldItem.name == newItem.name
                    else -> oldItem.javaClass == newItem.javaClass
                }
            },
            { oldItem: Any, newItem: Any ->
                when {
                    oldItem is User && newItem is User -> oldItem.username == newItem.username
                    oldItem is Bill && newItem is Bill -> oldItem.name == newItem.name
                    else -> oldItem.javaClass == newItem.javaClass && oldItem == newItem
                }
            }
        )

    fun <T : Any> getCommonDiffItemCallback() =
        SimpleDiffItemCallback(
            areItemSame = { oldItem: T, newItem: T ->
                when {
                    oldItem is User && newItem is User -> oldItem.username == newItem.username
                    oldItem is Bill && newItem is Bill -> oldItem.name == newItem.name
                    else -> oldItem.javaClass == newItem.javaClass
                }
            },
            areContentSame = { oldItem: T, newItem: T ->
                when {
                    oldItem is User && newItem is User -> oldItem.username == newItem.username
                    oldItem is Bill && newItem is Bill -> oldItem.name == newItem.name
                    else -> oldItem.javaClass == newItem.javaClass && oldItem == newItem
                }
            }
        )
}
