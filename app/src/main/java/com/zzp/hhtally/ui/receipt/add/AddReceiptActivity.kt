package com.zzp.hhtally.ui.receipt.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.github.gzuliyujiang.wheelpicker.OptionPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.exchangesystem.contracts.SelectPhotoContract
import com.zzp.exchangesystem.contracts.TakePhotoContract
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.Bill
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.data.TAG
import com.zzp.hhtally.data.UserData
import com.zzp.hhtally.databinding.ActivityAddReceiptBinding
import com.zzp.hhtally.databinding.BottomSheetContentBinding
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

    private lateinit var takePhotoLauncher: ActivityResultLauncher<Unit?>


    private lateinit var selectPhotoLauncher: ActivityResultLauncher<Unit?>

    private val modalBottomSheet by lazy {
        ModalBottomSheet(
            takePhotoLauncher, selectPhotoLauncher
        )
    }

    private lateinit var permission: ActivityResultLauncher<String>

    private lateinit var loadingDialog: AlertDialog

    override fun createPresenter() = AddReceiptPresenter(this)

    override fun initViewBinding() {
        binding = ActivityAddReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
        takePhotoLauncher = registerForActivityResult(TakePhotoContract()) {
            if (it != null) {
                presenter.analyzeImage(getBitmapFromUri(it))
            }
        }

        selectPhotoLauncher = registerForActivityResult(SelectPhotoContract()) {
            if (it != null) {
                presenter.analyzeImage(getBitmapFromUri(it))
            }
        }

        permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                modalBottomSheet.show(supportFragmentManager, TAG)
            }
        }
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.layoutExpend.isSelected = true
        EditTextUtils.afterDotTwo(binding.etMoney)
        binding.tvLabel.text = LabelUtil.labelList[0].labelName
        binding.tvTime.text = SimpleDateFormat("yyy-MM-dd", Locale.CHINA).format(Date())

        initDatePicker()
        initLabelPicker(LabelUtil.labelList)

        val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                modalBottomSheet.show(supportFragmentManager, TAG)
            }
        }

        binding.layoutExpend.setOnClickListener {
            binding.layoutExpend.isSelected = true
            binding.layoutIncome.isSelected = false
        }

        binding.layoutIncome.setOnClickListener {
            binding.layoutIncome.isSelected = true
            binding.layoutExpend.isSelected = false
        }

        binding.layoutPhoto.setOnClickListener {
            permission.launch(Manifest.permission.CAMERA)
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
                val moneyStr = binding.etMoney.text.toString()
                if (moneyStr.trim().isEmpty()) {
                    "金额不能为0".showToast()
                } else {
                    var money = moneyStr.toDouble()
                    val label = binding.tvLabel.text.toString()
                    if (label.trim().isNotEmpty()) {
                        val labelId = LabelUtil.getLabelIdFromName(label)
                        val time = binding.tvTime.text.toString()
                        val remark = binding.etRemark.text.toString()
                        val shopkeeper = binding.etShopkeeper.text.toString()
                        val payType = binding.layoutExpend.isSelected
                        if (!payType) money = -money
                        if (money == 0.0) {
                            "金额不能为0".showToast()
                        } else {
                            if (shopkeeper.trim().isEmpty()) {
                                "商家不能为空".showToast()
                            } else {
                                presenter.addBill(
                                    Bill(
                                        -1, labelId, -1, money, remark, time, shopkeeper
                                    )
                                )
                            }
                        }
                    } else {
                        "标签不能为空".showToast()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
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

    override fun doAddSuccess() {
        val intent = Intent()
        intent.putExtra("result", 0)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun refreshFromImg(price: String, time: String, type: String, shopkeeper: String) {
        if (price.isNotEmpty()) binding.etMoney.setText(price)
        if (time.isNotEmpty()) binding.tvTime.text = time.split(" ")[0]
        if (shopkeeper.isNotEmpty()) binding.etShopkeeper.setText(shopkeeper)
        if (type.isNotEmpty()) binding.tvLabel.text = type
    }

    override fun showLoadingDialog() {
        loadingDialog =
            MaterialAlertDialogBuilder(this).setView(ProgressBar(this)).setCancelable(false)
                .show()
    }

    override fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun refreshLabelPicker(labelList: List<Label>) {
        labelPicker?.setData(labelList.map {
            it.labelName
        })
    }

    class ModalBottomSheet(
        private val takePhotoLauncher: ActivityResultLauncher<Unit?>,
        private val selectPhotoLauncher: ActivityResultLauncher<Unit?>
    ) : BottomSheetDialogFragment() {

        private lateinit var binding: BottomSheetContentBinding
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            binding = BottomSheetContentBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.takePhoto.setOnClickListener {
                takePhotoLauncher.launch(null)
                dismiss()
            }
            binding.fromAlbum.setOnClickListener {
                selectPhotoLauncher.launch(null)
                dismiss()
            }
            binding.cancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}
