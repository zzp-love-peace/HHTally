package com.zzp.hhtally.ui.receipt.info

import android.content.Intent
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.data.TYPE_EXPENSE
import com.zzp.hhtally.databinding.ActivityAddReceiptBinding
import com.zzp.hhtally.databinding.ActivityReceiptInfoBinding
import com.zzp.hhtally.util.EditTextUtils
import com.zzp.hhtally.util.LabelUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class ReceiptInfoActivity : BaseActivity<IReceiptInfoView, ReceiptInfoPresenter>(),
    IReceiptInfoView {

    private lateinit var binding: ActivityReceiptInfoBinding

    private var labelPicker: OptionPicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null

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

        binding.layoutExpense.isSelected = type == TYPE_EXPENSE

        EditTextUtils.afterDotTwo(binding.etMoney)
        initDatePicker()
        initLabelPicker(LabelUtil.labelList)

        binding.layoutExpense.setOnClickListener {
            binding.layoutExpense.isSelected = true
            binding.layoutIncome.isSelected = false
        }

        binding.layoutIncome.setOnClickListener {
            binding.layoutIncome.isSelected = true
            binding.layoutExpense.isSelected = false
        }

        binding.layoutLabel.setOnClickListener {
            labelPicker?.show()
        }

        binding.layoutTime.setOnClickListener {
            datePicker?.show(supportFragmentManager, "tag")
        }

        binding.btnUpdate.setOnClickListener {
            MaterialAlertDialogBuilder(this).setTitle("提示")
                .setMessage("您确定要修改该账单信息吗？")
                .setNeutralButton("取消") { dialog, which ->
                }.setPositiveButton("确定") { dialog, which ->
                    val type = binding.layoutExpense.isSelected
                    if (type) {
                        bill.money = binding.etMoney.text.toString().toDouble()
                    } else {
                        bill.money = - binding.etMoney.text.toString().toDouble()
                    }
                    bill.time = binding.tvTime.text.toString()
                    bill.remark = binding.etRemark.text.toString()
                    bill.shopkeeper = binding.etShopkeeper.text.toString()
                    bill.labelId = LabelUtil.getLabelIdFromName(binding.tvLabel.text.toString())
                    presenter.updateBill(bill)
                }.show()
        }

        binding.etMoney.setText(abs(bill.money).toString())
        binding.tvLabel.text = LabelUtil.getLabelNameFormId(bill.labelId)
        binding.tvTime.text = bill.time
        binding.etShopkeeper.setText(bill.shopkeeper)
        binding.etRemark.setText(bill.remark)
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
                MaterialAlertDialogBuilder(this).setTitle("提示")
                    .setMessage("您确定要删除该账单吗？")
                    .setNeutralButton("取消") { dialog, which ->
                    }.setPositiveButton("确定") { dialog, which ->
                        presenter.removeBill(bill.billId)
                    }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun doRemoveSuccess() {
        val intent = Intent()
        intent.putExtra("billId", bill.billId)
        intent.putExtra("isRemove", true)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun doUpdateSuccess() {
        val intent = Intent()
        intent.putExtra("bill", bill)
        intent.putExtra("isRemove", false)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun initDatePicker() {
        datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("选择时间").build()

        datePicker?.addOnPositiveButtonClickListener {
            binding.tvTime.text = SimpleDateFormat("yyy-MM-dd", Locale.CHINA).format(it)
        }
    }

    private fun initLabelPicker(labels: List<Label>) {
        labelPicker = OptionPicker(this)
        labelPicker?.setTitle("标签")
        labelPicker?.setBodyWidth(140)
        labelPicker?.setData(labels.map {
            it.labelName
        })
        labelPicker?.setOnOptionPickedListener { _, item ->
            binding.tvLabel.text = item.toString()
        }
    }
}
