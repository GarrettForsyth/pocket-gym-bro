package com.example.android.pocketgymbro.testing.api

import androidx.lifecycle.LiveData
import com.example.android.pocketgymbro.api.ApiResponse
import com.example.android.pocketgymbro.api.WorkoutManagerService
import com.example.android.pocketgymbro.testing.utils.TestUtils
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestionResponse
import retrofit2.mock.BehaviorDelegate

class MockWorkoutManagerService(
    private val delegate: BehaviorDelegate<WorkoutManagerService>
) : WorkoutManagerService {

    override fun fetchExerciseSearchSuggestionByName(query: String): LiveData<ApiResponse<ExerciseSearchSuggestionResponse>> {

        val response =
            TestUtils.createExerciseSearchSuggestionResponse(
                TestUtils.createListOfNamedExerciseSearchSuggestions()
        )

        return delegate.returningResponse(response).fetchExerciseSearchSuggestionByName(query)
    }
}