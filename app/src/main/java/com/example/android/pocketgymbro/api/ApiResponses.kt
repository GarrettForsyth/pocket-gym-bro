package com.example.android.pocketgymbro.api

import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.google.gson.annotations.SerializedName

data class ExerciseSearchResponse(
    @SerializedName("suggestions")
    val exerciseSuggestions: List<ExerciseSearchSuggestion>
)
