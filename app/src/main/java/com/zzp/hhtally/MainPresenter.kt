package com.zzp.hhtally

import com.zzp.hhtally.base.BasePresenter


class MainPresenter(baseView: IMainView) : BasePresenter<IMainView>() {

    init {
        attachView(baseView)
    }


}
