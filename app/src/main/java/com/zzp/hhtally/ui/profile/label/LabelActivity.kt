package com.zzp.hhtally.ui.profile.label

import android.view.View
import android.widget.EditText
import androidx.core.view.setPadding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zzp.hhtally.R
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.data.Label
import com.zzp.hhtally.databinding.ActivityLabelBinding

class LabelActivity : BaseActivity<ILabelView, LabelPresenter>(), ILabelView {

    private lateinit var binding: ActivityLabelBinding

    private val addDialog by lazy {
        val textView = EditText(this@LabelActivity).apply {
            setPadding(resources.getDimensionPixelOffset(R.dimen.text_margin))
            hint = "输入标签名.."
        }

        MaterialAlertDialogBuilder(this@LabelActivity)
            .setView(textView)
            .setTitle("添加标签")
            .setPositiveButton("确定") { _, _ ->
                val labelName = textView.text.trim().toString()
                presenter.addLabel(labelName)

                textView.setText("")
            }
            .setNegativeButton("取消") { _, _ -> }
            .create()
    }

    private val labelAdapter by lazy {
        LabelAdapter() { label ->
            MaterialAlertDialogBuilder(this)
                .setTitle("删除 \"${label.labelName}\" 标签?")
                .setPositiveButton("确定") { _, _ ->
                    presenter.removeLabel(label)
                }
                .setNegativeButton("取消") { _, _ -> }
                .create()
                .show()
        }
    }

    override fun createPresenter() = LabelPresenter(this)

    override fun initViewBinding() {
        binding = ActivityLabelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initData() {
        presenter.getAllLabels()
    }

    override fun initView() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                finish()
            }
            val flexboxLayoutManager = FlexboxLayoutManager(this@LabelActivity).apply {
                justifyContent = JustifyContent.SPACE_AROUND
            }
            rvLabel.apply {
                layoutManager = flexboxLayoutManager
                adapter = labelAdapter
            }

            addLabel.setOnClickListener {
                addDialog.show()
            }
        }
    }

    override fun doRefreshSuccess() {
        binding.rvLabel.visibility = View.VISIBLE
        binding.loadingContainer.root.visibility = View.GONE
    }

    override fun refreshLabels(labelList: List<Label>) {
        labelAdapter.submitList(labelList)
    }

    override fun doRefreshError() {
        binding.rvLabel.visibility = View.GONE
        binding.loadingContainer.root.visibility = View.VISIBLE
    }

}
