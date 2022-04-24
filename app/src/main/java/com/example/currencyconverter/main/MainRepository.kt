package com.example.currencyconverter.main

import com.example.currencyconverter.data.models.CurrencyResponseModel
import com.example.currencyconverter.util.Resource

interface MainRepository {

    suspend fun getRatesViaRepository(base: String): Resource<CurrencyResponseModel>

}