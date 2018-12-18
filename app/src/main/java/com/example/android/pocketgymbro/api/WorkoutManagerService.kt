package com.example.android.pocketgymbro.api

import androidx.lifecycle.LiveData
import com.example.android.pocketgymbro.testing.OpenForTesting
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestionResponse
import retrofit2.http.GET
import retrofit2.http.Query

@OpenForTesting
interface WorkoutManagerService {

    @GET("exercise/search")
    fun fetchExerciseSearchSuggestionByName(@Query("term") query: String)
            : LiveData<ApiResponse<ExerciseSearchSuggestionResponse>>
}