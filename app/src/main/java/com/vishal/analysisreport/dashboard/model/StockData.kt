package com.vishal.analysisreport.dashboard.model

import com.google.gson.annotations.SerializedName

data class StockData(
    @SerializedName("stock_code")
    val stock_code: String,
    @SerializedName("company_name")
    val company_name: String
)
