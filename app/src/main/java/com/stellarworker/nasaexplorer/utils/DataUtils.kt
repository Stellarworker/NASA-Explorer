package com.stellarworker.nasaexplorer.utils

import com.stellarworker.nasaexplorer.R
import com.stellarworker.nasaexplorer.api.apod.PictureOfTheDayInfo
import com.stellarworker.nasaexplorer.api.apod.PictureOfTheDayResponse
import com.stellarworker.nasaexplorer.api.asteroids_neows.AsteroidsNeoWSResponse
import com.stellarworker.nasaexplorer.ui.MainFragmentDataset
import com.stellarworker.nasaexplorer.ui.asteroids_neows.AsteroidDataset
import com.stellarworker.nasaexplorer.ui.asteroids_neows.AsteroidsNeoWSFragmentDataset

private const val ONE_ITEM = 1
private const val EMPTY_STRING = ""
private const val ZERO_INT = 0
private const val INDENTED_LINE = "   "
private const val DOT = "."
private const val SUBST_STRING = "EMPTY_DATA!"
private const val YES = "Yes"
private const val NO = "No"

fun String.substituteIfEmpty(substString: String) = this.ifBlank { substString }

fun String.dropAfterDot() = this.replaceAfter(DOT, EMPTY_STRING).dropLast(ONE_ITEM)

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
fun convertPODTItoMainFD(info: PictureOfTheDayInfo) =
    with(info) {
        MainFragmentDataset(
            image = url.substituteIfEmpty(SUBST_STRING),
            title = title.substituteIfEmpty(SUBST_STRING),
            explanation = explanation.prependIndent(INDENTED_LINE).substituteIfEmpty(SUBST_STRING)
        )
    }

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

// Creates AsteroidsNeoWSFragmentDataset from AsteroidsNeoWSResponse
fun makeAsteroidsFragmentDataset(response: AsteroidsNeoWSResponse):
        AsteroidsNeoWSFragmentDataset {
    val entries = response.nearEarthObjects?.entries?.iterator()?.next()?.value ?: listOf()
    val asteroidDatasetList: MutableList<AsteroidDataset> = mutableListOf()
    entries.forEach {
        asteroidDatasetList.add(
            AsteroidDataset(
                id = it.id ?: SUBST_STRING,
                name = it.name ?: SUBST_STRING,
                absoluteMagnitudeH = it.absoluteMagnitudeH?.toString() ?: SUBST_STRING,
                estimatedDiameterMin = it.estimatedDiameter?.meters?.estimatedDiameterMin
                    ?.toString()?.dropAfterDot() ?: SUBST_STRING,
                estimatedDiameterMax = it.estimatedDiameter?.meters?.estimatedDiameterMax
                    ?.toString()?.dropAfterDot() ?: SUBST_STRING,
                hazardous = when (it.isPotentiallyHazardousAsteroid) {
                    true -> YES
                    false -> NO
                    else -> SUBST_STRING
                },
                relativeVelocity = it.closeApproachData?.get(ZERO_INT)?.relativeVelocity
                    ?.kilometersPerSecond?.dropAfterDot() ?: SUBST_STRING,
                closeApproachDate = it.closeApproachData?.get(ZERO_INT)?.closeApproachDate
                    ?: SUBST_STRING
            )
        )
    }
    return AsteroidsNeoWSFragmentDataset(asteroidDatasetList)
}


