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
            Log.d("deneme", "getRatesViaRepository - base $base")
            Log.d("deneme", "getRatesViaRepository - api $api")
            val response = api.getRatesFromRemote(base)
            Log.d("deneme", "getRatesViaRepository - response $response")
            val result = response.body()
            Log.d("deneme", "getRatesViaRepository - result $result")

            if (response.isSuccessful && result != null){
                Log.d("deneme", "response.isSuccessful")
                Resource.Success(result)
            }else {
                Log.d("deneme", "response.isNOTSuccessful")
                Resource.Error(response.message())
            }
        } catch (e: Exception){
            Resource.Error(e.message ?: "An error occured")
        }
    }

}