package com.stellarworker.nasaexplorer.domain


data class Notes(
    var noteList: MutableList<Note> = mutableListOf()
)
