package com.example.currencyconverter.main

import android.util.Log
import com.example.currencyconverter.data.CurrencyAPI
import com.example.currencyconverter.data.models.CurrencyResponseModel
import com.example.currencyconverter.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyAPI
) : MainRepository {

    override suspend fun getRatesViaRepository(base: String): Resource<CurrencyResponseModel> {
        return try {

            val response = api.getRatesFromRemote(base, "7a4261b8b4c990d8b892dc51bc29072e")
            val result = response.body()

            if (response.isSuccessful && result != null){
                Resource.Success(result)
            }else {
                Resource.Error(response.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message ?: "An error occured")
        }
    }

}