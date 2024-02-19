package com.vishal.analysisreport.data.repository

import com.vishal.analysisreport.dashboard.model.AnalysisReportResponse
import com.vishal.analysisreport.dashboard.model.StockDataResponse
import com.vishal.analysisreport.dashboard.model.StockExchangeResponse
import retrofit2.Response

interface StockApiRepository {
    suspend fun getStoreExchangeData(): Response<StockExchangeResponse>

    suspend fun findStockData(exchange: String, stockName: String): Response<StockDataResponse>

    suspend fun getAnalysisReportUrl(exchange: String, stockCode: String): Response<AnalysisReportResponse>

}