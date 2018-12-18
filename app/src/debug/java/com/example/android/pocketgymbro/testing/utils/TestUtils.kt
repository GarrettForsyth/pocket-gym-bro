package com.example.android.pocketgymbro.testing.utils

import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestionResponse

object TestUtils {

    fun createExerciseSearchSuggestionResponse(suggestions: List<ExerciseSearchSuggestion>)
        : ExerciseSearchSuggestionResponse {
        return ExerciseSearchSuggestionResponse(
            suggestions = suggestions
        )
    }

    fun createExerciseSearchSuggestion(id: Int, name: String): ExerciseSearchSuggestion {
        return ExerciseSearchSuggestion(
            exerciseData =  ExerciseSearchSuggestion.ExerciseSearchData(
                id = id,
                name = name,
                category = "1",
                image = "testImageLocation.com",
                imageThumbnail = "testThumbnailLocation.com"
            ),
            exerciseValue = name
        )
    }

    fun createListOfExerciseSearchSuggestions(length: Int): List<ExerciseSearchSuggestion> {
        val list = mutableListOf<ExerciseSearchSuggestion>()
        for (n in 1..length) {
            list.add(
                createExerciseSearchSuggestion(
                    n,
                    "test exercise $n"
                )
            )
        }
        return list
    }

    fun createListOfNamedExerciseSearchSuggestions(): List<ExerciseSearchSuggestion> {
        val names = listOf(
            "dead lift",
            "bench press",
            "squat",
            "curl",
            "shoulder press",
            "decline barbell press",
            "decline dumbell press",
            "romanian dead lift",
            "front squat",
            "box squat",
            "shrugs",
            "crunch"
        )

        return names.mapIndexed{ i, name ->
            createExerciseSearchSuggestion(i, name)
        }

    }
}
