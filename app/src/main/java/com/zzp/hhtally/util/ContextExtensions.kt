package com.zzp.hhtally.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.appcompat.widget.TintTypedArray

fun Context.getColorByAttr(@AttrRes attr: Int): Int =
    getColorStateListByAttr(attrs = intArrayOf( attr)).defaultColor

@SuppressLint("RestrictedApi")
fun Context.getColorStateListByAttr(
    set: AttributeSet? = null,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0
): ColorStateList =
    TintTypedArray.obtainStyledAttributes(this, set, attrs, defStyleAttr, defStyleRes).getColorStateList(0)
