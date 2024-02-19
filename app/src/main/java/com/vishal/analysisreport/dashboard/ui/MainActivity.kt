package com.vishal.analysisreport.dashboard.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.vishal.analysisreport.analysisReport.AnalysisReportActivity
import com.vishal.analysisreport.dashboard.viewmodel.MainViewModel
import com.vishal.analysisreport.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInitViewData()
        getStockExchangeList()
        initObserver()
        initSetOnClickListeners()
    }

    private fun setupInitViewData() {
        binding.progressBar.isVisible = false
        binding.autoCompleteStockName.isEnabled = false
        binding.btnGetAnalysisReport.isEnabled = false
        binding.autoCompleteStockName.addTextChangedListener {
            mainViewModel.getStockNameList(it.toString())
        }
    }

    private fun getStockExchangeList() {
        mainViewModel.getStockExchangeList()
    }


    private fun initObserver() {
        mainViewModel.liveDataStockExchange.observe(this) {
            if (it.data.isNotEmpty()) {
                val list = ArrayList<String>()
                it.data.forEach { list.add(it.exchange) }
                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    this, android.R.layout.simple_dropdown_item_1line,
                    list
                )
                binding.dropDownExchange.adapter = adapter
                binding.autoCompleteStockName.isEnabled = true
            }
        }
        mainViewModel.liveDataStockData.observe(this) {
            if (it.isNotEmpty()) {
                val list = ArrayList<String>()
                it.forEach { list.add(it.stock_code) }
                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    this, android.R.layout.simple_dropdown_item_1line,
                    list
                )
                binding.autoCompleteStockName.setAdapter(adapter)
            }
        }
        mainViewModel.liveDataReport.observe(this) {
            if (it.isNotEmpty() && it.contains("https")) {
                val intent = Intent(this, AnalysisReportActivity::class.java)
                intent.putExtra("url", it)
                startActivity(intent)
            }
        }
    }

    private fun initSetOnClickListeners() {
        binding.dropDownExchange.onItemSelectedListener = object : OnItemClickListener,
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                mainViewModel.setExchangeData(selectedItem)
                Toast.makeText(this@MainActivity, selectedItem, LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }
        }
        binding.autoCompleteStockName.onItemClickListener = object : OnItemClickListener,
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                mainViewModel.setSelectedStoreData(selectedItem)
                binding.btnGetAnalysisReport.isEnabled = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
                mainViewModel.setSelectedStoreData(selectedItem)
                binding.btnGetAnalysisReport.isEnabled = true
            }
        }
        binding.btnGetAnalysisReport.setOnClickListener {
            mainViewModel.getAnalysisReportUrl()
        }
    }
}
