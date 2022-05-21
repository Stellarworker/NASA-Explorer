package com.geekbrains.nasaexplorer.domain

import com.geekbrains.nasaexplorer.api.apod.PictureOfTheDayInfo
import com.geekbrains.nasaexplorer.api.asteroids_neows.AsteroidsNeoWSResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(): PictureOfTheDayInfo
    suspend fun asteroidsInfo(): AsteroidsNeoWSResponse
}