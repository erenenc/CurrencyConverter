package com.example.currencyconverter.di

import com.example.currencyconverter.data.CurrencyAPI
import com.example.currencyconverter.main.DefaultMainRepository
import com.example.currencyconverter.main.MainRepository
import com.example.currencyconverter.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyAPI = Retrofit.Builder()
        .baseUrl("http://api.exchangeratesapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyAPI::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: CurrencyAPI): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}