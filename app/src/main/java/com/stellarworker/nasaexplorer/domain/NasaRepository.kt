package com.stellarworker.nasaexplorer.domain

import com.stellarworker.nasaexplorer.api.apod.PictureOfTheDayInfo
import com.stellarworker.nasaexplorer.api.asteroids_neows.AsteroidsNeoWSResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(): PictureOfTheDayInfo
    suspend fun asteroidsInfo(): AsteroidsNeoWSResponse
}