package com.stellarworker.nasaexplorer.domain

private const val EMPTY_STRING = ""

data class Note(
    var text: String = EMPTY_STRING,
    var addedByUser: Boolean = false
)