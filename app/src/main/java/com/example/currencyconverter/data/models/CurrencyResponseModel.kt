package com.example.currencyconverter.data.models

data class CurrencyResponseModel(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)