package com.example.currencyconverter.data

import com.example.currencyconverter.data.models.CurrencyResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("/v1/latest")
    suspend fun getRatesFromRemote(
        @Query("base") base: String,
        @Query("access_key") access_key: String
    ): Response<CurrencyResponseModel>

}
//?access_key=7a4261b8b4c990d8b892dc51bc29072e