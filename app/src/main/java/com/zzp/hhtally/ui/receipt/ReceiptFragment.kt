package com.zzp.hhtally.ui.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseFragment
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter

// class ReceiptFragment : BaseFragment<IReceiptView, ReceiptPresenter>(), IReceiptView {
//
//     private val billAdapter = BillAdapter()
//
//     override fun createPresenter(): ReceiptPresenter {
//         return ReceiptPresenter(this)
//     }
//
//     override fun onCreateView(
//         inflater: LayoutInflater,
//         container: ViewGroup?,
//         savedInstanceState: Bundle?
//     ): View? {
//         // Inflate the layout for this fragment
//         return inflater.inflate(R.layout.fragment_receipt, container, false)
//     }
//
//     override fun initViewBinding(): View {
//
//     }
//
//     override fun initData() {
//
//     }
//
//     override fun initView() {
//
//     }
// }

class ReceiptFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receipt, container, false)
    }
}
