package com.zzp.hhtally.util

import android.util.Log
import android.widget.Toast
import com.zzp.hhtally.base.MyApplication
import com.zzp.hhtally.data.TAG


fun Throwable.logE() {
    Log.e(TAG, "Error: ", this)
}

fun String.logD(title: String = "logD") {
    Log.d(TAG, title + this)
}

fun String.showToast() {
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_SHORT).show()
}


