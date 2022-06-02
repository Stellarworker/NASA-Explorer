package com.stellarworker.nasaexplorer.api

import com.stellarworker.nasaexplorer.api.apod.PictureOfTheDayResponse
import com.stellarworker.nasaexplorer.api.asteroids_neows.AsteroidsNeoWSResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val NASA_APOD_URL = "planetary/apod"
private const val NASA_ASTEROIDS_NEOWS_URL = "neo/rest/v1/feed"
private const val API_KEY = "api_key"

interface NasaApi {

    @GET(NASA_APOD_URL)
    suspend fun pictureOfTheDay(@Query(API_KEY) key: String): PictureOfTheDayResponse

    @GET(NASA_ASTEROIDS_NEOWS_URL)
    suspend fun asteroidsInfo(@Query(API_KEY) key: String): AsteroidsNeoWSResponse
}