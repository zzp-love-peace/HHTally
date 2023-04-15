package com.zzp.hhtally.data.chart

import java.io.Serializable

data class WeekInfo(
    val weekSum: Double,
    val dayAverage: Double,
    val everyday: List<Double>
) : Serializable
