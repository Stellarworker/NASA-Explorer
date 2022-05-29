package com.geekbrains.nasaexplorer.ui

private const val EMPTY_STRING = ""

data class MainFragmentDataset(
    val image: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val explanation: String = EMPTY_STRING,
)