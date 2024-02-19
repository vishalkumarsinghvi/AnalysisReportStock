package com.vishal.analysisreport.data.repository

import com.vishal.analysisreport.dashboard.model.AnalysisReportResponse
import com.vishal.analysisreport.dashboard.model.StockDataResponse
import com.vishal.analysisreport.dashboard.model.StockExchangeResponse
import com.vishal.analysisreport.data.service.StockService
import retrofit2.Response
import javax.inject.Inject

class StockApiRepositoryImpl @Inject constructor(private val stockService: StockService) :
    StockApiRepository {
    override suspend fun getStoreExchangeData(): Response<StockExchangeResponse> {
        return stockService.getStockExchange()
    }

    override suspend fun findStockData(
        exchange: String,
        stockName: String
    ): Response<StockDataResponse> {
        return stockService.findStockData(exchange = exchange, searchTerm = stockName)
    }

    override suspend fun getAnalysisReportUrl(
        exchange: String,
        stockCode: String
    ): Response<AnalysisReportResponse> {
        return stockService.getAnalysisReportUrl(exchange = exchange, stockCode = stockCode)
    }
}