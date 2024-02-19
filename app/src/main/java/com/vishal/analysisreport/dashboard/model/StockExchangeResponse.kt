package com.vishal.analysisreport.dashboard.model

import com.google.gson.annotations.SerializedName

data class StockExchangeResponse(
    @SerializedName("data")
    val data: List<StockExchangeData>,
    val message: String,
    val status: String
)