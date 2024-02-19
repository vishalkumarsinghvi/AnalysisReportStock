package com.vishal.analysisreport.dashboard.model

import com.google.gson.annotations.SerializedName

data class StockDataResponse(
    @SerializedName("data")
    val data: List<StockData>,
    val message: String,
    val status: String
)