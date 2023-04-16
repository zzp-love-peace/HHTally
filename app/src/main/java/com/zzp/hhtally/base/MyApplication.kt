package com.zzp.hhtally.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.github.gzuliyujiang.dialog.DialogColor
import com.github.gzuliyujiang.dialog.DialogConfig
import com.github.gzuliyujiang.dialog.DialogStyle
import com.zzp.hhtally.R

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}