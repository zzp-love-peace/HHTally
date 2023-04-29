package com.zzp.hhtally.data

import java.io.Serializable

data class Bill(
    val billId: Int,//唯一标识符
    var labelId: Int,//标签
    val userId: Int,
    var money: Double,//金额
    var remark: String?,//备注
    var time: String,
    var shopkeeper: String
) : Serializable
