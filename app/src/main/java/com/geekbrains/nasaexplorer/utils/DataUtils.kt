package com.geekbrains.nasaexplorer.utils

import com.geekbrains.nasaexplorer.R
import com.geekbrains.nasaexplorer.api.PictureOfTheDayInfo
import com.geekbrains.nasaexplorer.api.PictureOfTheDayResponse
import com.geekbrains.nasaexplorer.common.EMPTY_STRING
import com.geekbrains.nasaexplorer.common.INDENTED_LINE
import com.geekbrains.nasaexplorer.common.SUBST_STRING
import com.geekbrains.nasaexplorer.ui.MainFragmentDataset

fun substituteIfEmpty(string: String) = string.ifBlank { SUBST_STRING }

// Converts PictureOfTheDayResponse to PictureOfTheDayInfo
fun convertPOTDRtoPOTDI(response: PictureOfTheDayResponse) =
    with(response) {
        PictureOfTheDayInfo(
            copyright = copyright ?: EMPTY_STRING,
            date = date,
            explanation = explanation ?: EMPTY_STRING,
            hdurl = hdurl ?: EMPTY_STRING,
            mediaType = mediaType ?: EMPTY_STRING,
            serviceVersion = serviceVersion ?: EMPTY_STRING,
            title = title ?: EMPTY_STRING,
            url = url ?: EMPTY_STRING
        )
    }

// Converts PictureOfTheDayInfo to MainFragmentDataset
fun convertPODTItoMFD(info: PictureOfTheDayInfo) =
    MainFragmentDataset(
        image = substituteIfEmpty(info.url),
        title = substituteIfEmpty(info.title),
        explanation = substituteIfEmpty(info.explanation.prependIndent(INDENTED_LINE))
    )

// Converts Theme IDs to RadioButton IDs
fun convertTIDtoRBID(themeId: Int) =
    when (themeId) {
        R.style.Theme_NASAExplorer_Moon -> R.id.moon_theme
        R.style.Theme_NASAExplorer_Mars -> R.id.mars_theme
        else -> R.id.system_default_theme
    }

// Converts RadioButton IDs to Theme IDs
fun convertRBIDtoTID(positionId: Int) =
    when (positionId) {
        R.id.moon_theme -> R.style.Theme_NASAExplorer_Moon
        R.id.mars_theme -> R.style.Theme_NASAExplorer_Mars
        else -> R.style.Theme_NASAExplorer_Default
    }