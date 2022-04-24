package com.example.currencyconverter

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {
            viewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
            Log.d("deneme", "btnConvert.setOnClickListener")
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect { event ->
                when(event){
                    is MainViewModel.CurrencyEvent.Success -> {
                        Log.d("deneme", "viewModel.conversion 1")
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText
                    }
                    is MainViewModel.CurrencyEvent.Failure -> {
                        Log.d("deneme", "viewModel.conversion 2")
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText + "test"
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        Log.d("deneme", "viewModel.conversion 3")
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }


    }
}