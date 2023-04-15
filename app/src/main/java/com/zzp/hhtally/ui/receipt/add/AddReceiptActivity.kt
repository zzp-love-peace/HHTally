package com.zzp.hhtally.ui.receipt.add

import android.view.MenuItem
import com.zzp.hhtally.base.BaseActivity
import com.zzp.hhtally.databinding.ActivityAddReceiptBinding

class AddReceiptActivity : BaseActivity<IAddReceiptView, AddReceiptPresenter>(), IAddReceiptView {

    private lateinit var binding: ActivityAddReceiptBinding

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

        binding.layoutPhoto.setOnClickListener {  }

        binding.layoutLabel.setOnClickListener {  }

        binding.layoutTime.setOnClickListener {  }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}