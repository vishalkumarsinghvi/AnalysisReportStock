package com.vishal.analysisreport.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.analysisreport.dashboard.model.StockData
import com.vishal.analysisreport.dashboard.model.StockExchangeResponse
import com.vishal.analysisreport.data.repository.StockApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val stockApiRepositoryImpl: StockApiRepositoryImpl) :
    ViewModel() {

    private val _liveDataStockExchange = MutableLiveData<StockExchangeResponse>()
    val liveDataStockExchange: LiveData<StockExchangeResponse> = _liveDataStockExchange

    private val _liveDataStockData = MutableLiveData<List<StockData>>()
    val liveDataStockData: LiveData<List<StockData>> = _liveDataStockData

    private val _liveDataCurrentExchange = MutableLiveData<String>()
    val liveDataCurrentExchange: LiveData<String> = _liveDataCurrentExchange

    private val _liveDataSelectedStockData = MutableLiveData<String>()
    val liveDataSelectedStockData: LiveData<String> = _liveDataSelectedStockData

    private val _liveDataReport = MutableLiveData<String>()
    val liveDataReport: LiveData<String> = _liveDataReport

    fun getStockExchangeList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = stockApiRepositoryImpl.getStoreExchangeData()
            if (response.isSuccessful) {
                response.body()?.let {
                    println("data--${it.data}")
                    _liveDataStockExchange.postValue(it)
                }
            }
            println("data--${response.toString()}")
        }
    }

    fun getStockNameList(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            liveDataCurrentExchange.value?.let { exchange ->
                val response = stockApiRepositoryImpl.findStockData(exchange, value)
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _liveDataStockData.postValue(it)
                    }
                } else {
                    Log.d("TAG", "getStockNameList: " + response.errorBody())
                }
            }
        }
    }

    fun getAnalysisReportUrl() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_liveDataCurrentExchange.value != null && _liveDataSelectedStockData.value != null) {
                val response = stockApiRepositoryImpl.getAnalysisReportUrl(
                    _liveDataCurrentExchange.value!!,
                    _liveDataSelectedStockData.value!!
                )
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        _liveDataReport.postValue(it)
                    }
                } else {
                    Log.d("TAG", "getStockNameList: " + response.errorBody())
                }
            }

        }
    }

    fun setExchangeData(value: String) {
        _liveDataCurrentExchange.postValue(value)
    }

    fun setSelectedStoreData(stockCode: String) {
        _liveDataSelectedStockData.postValue(stockCode)
    }
}