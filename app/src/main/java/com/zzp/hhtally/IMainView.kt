package com.zzp.hhtally

import android.view.MenuItem
import com.zzp.hhtally.base.BaseView

interface IMainView : BaseView {

    fun switchFragment(fragmentIndex: Int)

    fun onBottomItemSelect(item: MenuItem): Boolean

    fun onBottomDoubleClick(item: MenuItem)
}
