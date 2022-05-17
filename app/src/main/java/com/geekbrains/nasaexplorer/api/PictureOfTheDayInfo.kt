package com.geekbrains.nasaexplorer.api

import com.geekbrains.nasaexplorer.common.EMPTY_STRING
import java.util.*

data class PictureOfTheDayInfo(
    val copyright: String = EMPTY_STRING,
    val date: Date? = null,
    val explanation: String = EMPTY_STRING,
    val hdurl: String = EMPTY_STRING,
    val mediaType: String = EMPTY_STRING,
    val serviceVersion: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val url: String = EMPTY_STRING
)