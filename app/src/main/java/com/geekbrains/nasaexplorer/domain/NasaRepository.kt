package com.geekbrains.nasaexplorer.domain

import com.geekbrains.nasaexplorer.api.PictureOfTheDayInfo

interface NasaRepository {
    suspend fun pictureOfTheDay(): PictureOfTheDayInfo
}