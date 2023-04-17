package com.zzp.hhtally.ui.receipt.info

import android.content.Intent
import android.os.Build
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.databinding.ActivityReceiptInfoBinding
import com.zzp.hhtally.ui.receipt.add.AddReceiptActivity
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.logD
import kotlin.math.abs


class ReceiptInfoActivity : BaseActivity<IReceiptInfoView, ReceiptInfoPresenter>(),
    IReceiptInfoView {

    private lateinit var binding: ActivityReceiptInfoBinding
    private lateinit var bill: Bill
    private var type: Int = -1

    override fun createPresenter() = ReceiptInfoPresenter(this)


    override fun initViewBinding() {
        binding = ActivityReceiptInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun initData() {
        bill = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("bill", Bill::class.java)!!
        } else {
            intent.getSerializableExtra("bill") as Bill
        }
        type = intent.getIntExtra("type", -1)
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (type == TYPE_EXPENSE) {
            binding.toolbar.title = "支出详情"
        } else {
            binding.toolbar.title = "收入详情"
        }

        binding.tvMoney.text = abs(bill.money).toString()
        binding.tvLabel.text = LabelUtil.getLabelNameFormId(bill.labelId)
        binding.tvTime.text = bill.time
        binding.tvShopkeeper.text = bill.shopkeeper
        binding.tvShopkeeper.text = bill.remark
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.info_receipt_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_delete -> {
                MaterialAlertDialogBuilder(this).setTitle("提示").setMessage("您确定要删除该账单吗？")
                    .setNeutralButton("取消") { dialog, which ->
                    }.setPositiveButton("确定") { dialog, which ->
                        presenter.removeBill(bill.billId)
                    }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun doRemoveSuccess() {
        setResult(RESULT_OK)
        finish()
    }
}