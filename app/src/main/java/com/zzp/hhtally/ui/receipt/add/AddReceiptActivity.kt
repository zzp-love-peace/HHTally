package com.zzp.hhtally.ui.receipt.add

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.databinding.ActivityAddReceiptBinding
import com.zzp.hhtally.util.EditTextUtils
import com.zzp.hhtally.util.LabelUtil
import com.zzp.hhtally.util.logD
import com.zzp.hhtally.util.showToast
import java.text.SimpleDateFormat
import java.util.*

class AddReceiptActivity : BaseActivity<IAddReceiptView, AddReceiptPresenter>(), IAddReceiptView {

    private lateinit var binding: ActivityAddReceiptBinding
    private var labelPicker: OptionPicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null

    override fun createPresenter() = AddReceiptPresenter(this)

    override fun initViewBinding() {
        binding = ActivityAddReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        EditTextUtils.afterDotTwo(binding.etMoney)
        binding.tvLabel.text = LabelUtil.labelList[0].labelName
        binding.tvTime.text = SimpleDateFormat("yyy/MM/dd", Locale.CHINA).format(Date())

        initDatePicker()
        initLabelPicker(LabelUtil.labelList)

        binding.layoutPhoto.setOnClickListener {

        }

        binding.layoutLabel.setOnClickListener {
            labelPicker?.show()
        }

        binding.layoutTime.setOnClickListener {
            datePicker?.show(supportFragmentManager, "tag")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_receipt_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_commit -> {
                val money = binding.etMoney.text.toString().toDouble()
                val labelId = LabelUtil.getLabelIdFromName(binding.tvLabel.text.toString())
                val time = binding.tvTime.text.toString()
                val remark = binding.etRemark.text.toString()
                val shopkeeper = binding.etShopkeeper.text.toString()
                if (shopkeeper.trim().isNotEmpty()) {
                    presenter.addBill(Bill(-1, labelId, -1, money, remark, time, shopkeeper))
                } else {
                    "商家不能为空".showToast()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDatePicker() {
        datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("选择时间")
                .build()

        datePicker?.addOnPositiveButtonClickListener {
            binding.tvTime.text = SimpleDateFormat.getDateInstance().format(it)

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

    override fun doAddSuccess() {
        finish()
    }
}