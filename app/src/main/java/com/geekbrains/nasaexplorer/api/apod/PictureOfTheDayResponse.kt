package com.geekbrains.nasaexplorer.api.apod

import com.google.gson.annotations.SerializedName
import java.util.*

data class PictureOfTheDayResponse(
    @SerializedName("copyright")
    val copyright: String? = null,

    @SerializedName("date")
    val date: Date? = null,

    @SerializedName("explanation")
    val explanation: String? = null,

    @SerializedName("hdurl")
    val hdurl: String? = null,

    @SerializedName("media_type")
    val mediaType: String? = null,

    @SerializedName("service_version")
    val serviceVersion: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null
)