package com.geekbrains.nasaexplorer.domain

import com.geekbrains.nasaexplorer.BuildConfig
import com.geekbrains.nasaexplorer.api.NasaApi
import com.geekbrains.nasaexplorer.api.apod.PictureOfTheDayInfo
import com.geekbrains.nasaexplorer.api.asteroids_neows.AsteroidsNeoWSResponse
import com.geekbrains.nasaexplorer.utils.convertPOTDRtoPOTDI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_NASA_URL = "https://api.nasa.gov/"

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

    override suspend fun pictureOfTheDay(): PictureOfTheDayInfo =
        convertPOTDRtoPOTDI(api.pictureOfTheDay(BuildConfig.NASA_API_KEY))


    override suspend fun asteroidsInfo(): AsteroidsNeoWSResponse =
        api.asteroidsInfo(BuildConfig.NASA_API_KEY)
}