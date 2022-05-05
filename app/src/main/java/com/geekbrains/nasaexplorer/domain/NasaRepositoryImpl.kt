package com.geekbrains.nasaexplorer.domain

import com.geekbrains.nasaexplorer.BuildConfig
import com.geekbrains.nasaexplorer.api.NasaApi
import com.geekbrains.nasaexplorer.api.PictureOfTheDayResponse
import com.geekbrains.nasaexplorer.common.BASE_NASA_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaRepositoryImpl : NasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_NASA_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()
        )
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(): PictureOfTheDayResponse =
        api.pictureOfTheDay(BuildConfig.NASA_API_KEY)
}