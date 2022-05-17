package com.geekbrains.nasaexplorer.ui

import com.geekbrains.nasaexplorer.common.EMPTY_STRING

data class MainFragmentDataset(
    val image: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val explanation: String = EMPTY_STRING,
)