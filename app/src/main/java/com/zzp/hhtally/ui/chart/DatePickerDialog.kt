package com.zzp.hhtally.ui.chart

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.databinding.DialogDatePickerBinding
import com.zzp.hhtally.util.logD
import com.zzp.hhtally.util.showToast
import java.util.Calendar
import java.util.TimeZone

class DatePickerDialog(context: Context, ok: (String, String) -> Unit, neutral: (String) -> Unit) {

    private val dialogDatePickerBinding =
        DialogDatePickerBinding.inflate(LayoutInflater.from(context))

    private val datePickerDialog by lazy {
        MaterialAlertDialogBuilder(context)
            .setView(dialogDatePickerBinding.root)
            .setPositiveButton("确定") { _, _ ->
                ok(year.toString(), month.toString())
                "$year/$month".showToast()
            }
            .setNeutralButton("全年") { _, _ ->
                neutral(year.toString())
            }
            .setNegativeButton("取消") { _, _ -> }
            .create()
    }

    private var year = 0
    private var month = 0


    init {
        dialogDatePickerBinding.apply {
            yearPicker.apply {
                minValue = 2000
                maxValue = 2100
                // 这个必须放在前两个属性之后, 不然没用
                value = Calendar.getInstance(TimeZone.getDefault())[Calendar.YEAR]
                setOnValueChangedListener { _, oldVal, newVal ->
                    oldVal.logD("yearPicker")
                    newVal.logD("yearPicker")
                    if (year != newVal) {
                        year = newVal
                    }
                }
            }

            monthPicker.apply {
                minValue = 1
                maxValue = 12
                // 这个必须放在前两个属性之后, 不然没用
                value = Calendar.getInstance(TimeZone.getDefault())[Calendar.MONTH] + 1
                setOnValueChangedListener { _, oldVal, newVal ->
                    oldVal.logD("yearPicker")
                    newVal.logD("yearPicker")
                    if (month != newVal) {
                        month = newVal
                    }
                }
            }

            year = yearPicker.value
            month = monthPicker.value
        }
    }

    fun show() {
        datePickerDialog.show()
    }
}
