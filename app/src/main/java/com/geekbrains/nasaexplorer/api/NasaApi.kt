package com.geekbrains.nasaexplorer.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val NASA_APOD_URL = "planetary/apod"
private const val API_KEY = "api_key"

interface NasaApi {

    @GET(NASA_APOD_URL)
    suspend fun pictureOfTheDay(@Query(API_KEY) key: String): PictureOfTheDayResponse
}