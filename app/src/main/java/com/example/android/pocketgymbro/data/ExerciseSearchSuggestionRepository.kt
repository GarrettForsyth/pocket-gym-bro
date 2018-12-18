package com.example.android.pocketgymbro.data

import androidx.lifecycle.LiveData
import com.example.android.pocketgymbro.AppExecutors
import com.example.android.pocketgymbro.api.ApiResponse
import com.example.android.pocketgymbro.api.WorkoutManagerService
import com.example.android.pocketgymbro.db.ExerciseSearchSuggestionDao
import com.example.android.pocketgymbro.testing.OpenForTesting
import com.example.android.pocketgymbro.util.NetworkBoundResource
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestionResponse
import com.example.android.pocketgymbro.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class ExerciseSearchSuggestionRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val exerciseSearchSuggestionDao: ExerciseSearchSuggestionDao,
    private val workoutManagerService: WorkoutManagerService
) {

    fun search(query: String): LiveData<Resource<List<ExerciseSearchSuggestion>>> {
        return object : NetworkBoundResource<List<ExerciseSearchSuggestion>, ExerciseSearchSuggestionResponse>(appExecutors){

            override fun saveCallResult(item: ExerciseSearchSuggestionResponse) {
                return exerciseSearchSuggestionDao.insert(item.suggestions)
            }

            override fun shouldFetch(data: List<ExerciseSearchSuggestion>?): Boolean {
                return true
            }

            // TODO: appending %'s is a bit ugly
            override fun loadFromDb(): LiveData<List<ExerciseSearchSuggestion>> {
                return  exerciseSearchSuggestionDao.getByName("%${query}%")
            }

            override fun createCall(): LiveData<ApiResponse<ExerciseSearchSuggestionResponse>> {
                return workoutManagerService.fetchExerciseSearchSuggestionByName(query)
            }

        }.asLiveData()
    }
}
