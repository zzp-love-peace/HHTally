package com.zzp.hhtally.data

data class Bill(
    val billId: Int,//唯一标识符
    val labelId: Int,//标签
    val label: String,
    val money: Double,//金额
    val remark: String,//备注
    val shopkeeper: String
)
