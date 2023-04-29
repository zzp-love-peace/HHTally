package com.zzp.hhtally.ui.receipt.search

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.*
import com.zzp.hhtally.databinding.ActivitySearchBinding
import com.zzp.hhtally.ui.receipt.adapter.BillAdapter
import com.zzp.hhtally.util.LabelUtil
import java.text.SimpleDateFormat
import java.util.*


class SearchActivity : BaseActivity<ISearchView, SearchPresenter>(),
    ISearchView {

    private lateinit var binding: ActivitySearchBinding
    private var labelPicker: OptionPicker? = null
    private var datePicker: MaterialDatePicker<Long>? = null

    private lateinit var billAdapter: BillAdapter

//    private val labelBillList = ArrayList<Bill>()
//    private val timeBillList = ArrayList<Bill>()

    private var pageNum = 1
    private var startTime = ""
    private var endTime = ""
    private var label = ""

    private lateinit var receiptInfoActivityLauncher: ActivityResultLauncher<Intent>

    override fun createPresenter() = SearchPresenter(this)

    override fun initViewBinding() {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initData() {
        initDatePicker()
        initLabelPicker(LabelUtil.labelList)
        receiptInfoActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val id = activityResult.data?.getIntExtra("billId", -1)
                    if (id != -1) {
                        val list = arrayListOf<Bill>()
                        list.addAll(billAdapter.currentList)
                        list.removeIf {
                            it.billId == id
                        }
                        billAdapter.submitList(list)
                    }
                }
            }
        billAdapter = BillAdapter(this, receiptInfoActivityLauncher)
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
        binding.layoutRefresh.setEnableLoadMoreWhenContentNotFull(false)
        binding.layoutRefresh.setOnRefreshListener {
            pageNum = 1
            presenter.getBillsByCondition(
                pageNum,
                startTime,
                endTime,
                if (label.isNotEmpty()) LabelUtil.getLabelIdFromName(label) else null
            )
        }

        binding.layoutRefresh.setOnLoadMoreListener {
            pageNum++
            presenter.getBillsByCondition(
                pageNum,
                startTime,
                endTime,
                if (label.isNotEmpty()) LabelUtil.getLabelIdFromName(label) else null
            )
        }
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
            startTime = time
            pageNum = 1
            presenter.getBillsByCondition(
                pageNum,
                startTime,
                endTime,
                if (label.isNotEmpty()) LabelUtil.getLabelIdFromName(label) else null
            )
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
            label = item.toString()
            pageNum = 1
            presenter.getBillsByCondition(
                pageNum,
                startTime,
                endTime,
                if (label.isNotEmpty()) LabelUtil.getLabelIdFromName(label) else null
            )
        }
    }

    override fun doSearchSuccess(billList: List<Bill>, isFirst: Boolean) {
        if (isFirst) {
            billAdapter.submitList(billList)
        } else {
            val list = arrayListOf<Bill>()
            list.addAll(billAdapter.currentList)
            list.addAll(billList)
            billAdapter.submitList(list)
        }
        if (binding.layoutRefresh.isRefreshing) {
            binding.layoutRefresh.finishRefresh()
        }
        if (binding.layoutRefresh.isLoading) {
            binding.layoutRefresh.finishLoadMore()
        }
    }

//    private fun mergeList(): List<Bill> {
//        if (labelBillList.isEmpty()) return timeBillList.toList()
//        if (timeBillList.isEmpty()) return labelBillList.toList()
//        val list = arrayListOf<Bill>()
//        for(bill in timeBillList) {
//            if (labelBillList.contains(bill)) {
//                list.add(bill)
//            }
//        }
//        return list.toList()
//    }

}