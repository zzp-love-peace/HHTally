package com.zzp.hhtally.ui.receipt.search

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.*
import com.zzp.hhtally.databinding.ActivitySearchBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import com.zzp.hhtally.util.LabelUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity : BaseActivity<ISearchView, SearchPresenter>(),
    ISearchView {

    private lateinit var binding: ActivitySearchBinding
    private var labelPicker: OptionPicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var billAdapter: BillAdapter

    private val labelBillList = ArrayList<Bill>()
    private val timeBillList = ArrayList<Bill>()

    private lateinit var removeReceiptActivityLauncher: ActivityResultLauncher<Intent>

    override fun createPresenter() = SearchPresenter(this)

    override fun initViewBinding() {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
        initDatePicker()
        initLabelPicker(LabelUtil.labelList)
        removeReceiptActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    presenter.getBillsByDate(binding.tvTime.text.toString())
                }
            }
        billAdapter = BillAdapter(this, removeReceiptActivityLauncher)
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.layoutSelectTime.setOnClickListener {
            datePicker?.show(supportFragmentManager, "tag2")
        }

        binding.layoutSelectLabel.setOnClickListener {
            labelPicker?.show()
        }
        binding.rvBill.adapter = billAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
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
            val time = SimpleDateFormat("yyy-MM-dd", Locale.CHINA).format(it)
            binding.tvTime.text = time
            presenter.getBillsByDate(time)
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
            presenter.getBillsByLabel(LabelUtil.getLabelIdFromName(item.toString()))
        }
    }

    override fun doSearchSuccess(billList: List<Bill>, type: Int) {
        if (type == TYPE_LABEL_SEARCH) {
            labelBillList.clear()
            labelBillList.addAll(billList)
        } else if (type == TYPE_TIME_SEARCH) {
            timeBillList.clear()
            timeBillList.addAll(billList)
        }
        billAdapter.submitList(mergeList())
    }

    private fun mergeList(): List<Bill> {
        if (labelBillList.isEmpty()) return timeBillList.toList()
        if (timeBillList.isEmpty()) return labelBillList.toList()
        val list = arrayListOf<Bill>()
        for(bill in timeBillList) {
            if (labelBillList.contains(bill)) {
                list.add(bill)
            }
        }
        return list.toList()
    }

}