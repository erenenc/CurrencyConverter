package com.example.currencyconverter.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.models.Rates
import com.example.currencyconverter.util.DispatcherProvider
import com.example.currencyconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading : CurrencyEvent()
        object Empty : CurrencyEvent()

    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        Log.d("deneme", "convert 1 fromAmount :  $fromAmount")
        if (fromAmount == null){
            Log.d("deneme", "convert 2 fromAmount :  $fromAmount")
            _conversion.value = CurrencyEvent.Failure("not a valid amount")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            Log.d("deneme", "convert 22")
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = repository.getRatesViaRepository(fromCurrency)) {
                is Resource.Error -> {
                    Log.d("deneme", "convert 4")
                    _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                }
                is Resource.Success -> {
                    Log.d("deneme", "convert 3")
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency, rates)
                    Log.d("deneme", "rate $rate")
                    if (rate == null){
                        _conversion.value = CurrencyEvent.Failure("unexpected error")
                    } else {
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        Log.d("deneme", "convertedCurrency $convertedCurrency")
                        _conversion.value = CurrencyEvent.Success(
                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
                        )
                    }
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.CAD
        "HKD" -> rates.HKD
        "ISK" -> rates.ISK
        "EUR" -> rates.EUR
        "PHP" -> rates.PHP
        "DKK" -> rates.DKK
        "HUF" -> rates.HUF
        "CZK" -> rates.CZK
        "AUD" -> rates.AUD
        "RON" -> rates.RON
        "SEK" -> rates.SEK
        "IDR" -> rates.IDR
        "INR" -> rates.INR
        "BRL" -> rates.BRL
        "RUB" -> rates.RUB
        "HRK" -> rates.HRK
        "JPY" -> rates.JPY
        "THB" -> rates.THB
        "CHF" -> rates.CHF
        "SGD" -> rates.SGD
        "PLN" -> rates.PLN
        "BGN" -> rates.BGN
        "CNY" -> rates.CNY
        "NOK" -> rates.NOK
        "NZD" -> rates.NZD
        "ZAR" -> rates.ZAR
        "USD" -> rates.USD
        "MXN" -> rates.MXN
        "ILS" -> rates.ILS
        "GBP" -> rates.GBP
        "KRW" -> rates.KRW
        "MYR" -> rates.MYR
        else -> null
    }


}