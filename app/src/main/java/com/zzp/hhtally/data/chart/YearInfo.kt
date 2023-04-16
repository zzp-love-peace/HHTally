package com.zzp.hhtally.data.chart

import java.io.Serializable

data class YearInfo(
    val yearSum: Double,
    val monthSum: List<Double>,
    val monthAverage: Double
) : Serializable
