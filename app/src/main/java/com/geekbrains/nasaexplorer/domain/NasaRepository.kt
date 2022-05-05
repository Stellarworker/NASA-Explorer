package com.geekbrains.nasaexplorer.domain

import com.geekbrains.nasaexplorer.api.PictureOfTheDayResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(): PictureOfTheDayResponse
}