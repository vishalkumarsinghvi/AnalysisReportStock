package com.vishal.analysisreport.dashboard.model

import com.google.gson.annotations.SerializedName

data class StockExchangeData(
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("country")
    val country: String
)
