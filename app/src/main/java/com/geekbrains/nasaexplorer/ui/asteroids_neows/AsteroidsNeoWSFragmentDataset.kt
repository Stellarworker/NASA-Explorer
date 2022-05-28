package com.geekbrains.nasaexplorer.ui.asteroids_neows

private const val EMPTY_STRING = ""

data class AsteroidDataset(
    val id: String = EMPTY_STRING,
    val name: String = EMPTY_STRING,
    val absoluteMagnitudeH: String = EMPTY_STRING,
    val estimatedDiameterMin: String = EMPTY_STRING,
    val estimatedDiameterMax: String = EMPTY_STRING,
    val hazardous: String = EMPTY_STRING,
    val relativeVelocity: String = EMPTY_STRING,
    val closeApproachDate: String = EMPTY_STRING
)

data class AsteroidsNeoWSFragmentDataset(
    val asteroids: List<AsteroidDataset> = listOf()
)