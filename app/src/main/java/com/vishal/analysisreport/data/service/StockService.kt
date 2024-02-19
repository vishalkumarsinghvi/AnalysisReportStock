package com.vishal.analysisreport.data.service

import com.vishal.analysisreport.common.Constants
import com.vishal.analysisreport.dashboard.model.AnalysisReportResponse
import com.vishal.analysisreport.dashboard.model.StockDataResponse
import com.vishal.analysisreport.dashboard.model.StockExchangeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET(Constants.SupportedExchanges)
    suspend fun getStockExchange(): Response<StockExchangeResponse>


    @GET(Constants.FindStocks)
    suspend fun findStockData(
        @Query("exchange") exchange: String,
        @Query("search_term") searchTerm: String
    ): Response<StockDataResponse>


    @GET(Constants.AnalysisReport)
    suspend fun getAnalysisReportUrl(
        @Query("exchange") exchange: String,
        @Query("stock_code") stockCode: String
    ): Response<AnalysisReportResponse>
}