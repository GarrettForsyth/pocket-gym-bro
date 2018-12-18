package com.example.android.pocketgymbro.ui.search

import androidx.lifecycle.*
import com.example.android.pocketgymbro.data.ExerciseSearchSuggestionRepository
import com.example.android.pocketgymbro.testing.OpenForTesting
import com.example.android.pocketgymbro.util.AbsentLiveData
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.Resource
import javax.inject.Inject

@OpenForTesting
class SearchViewModel @Inject constructor(
    exerciseSearchSuggestionRepository: ExerciseSearchSuggestionRepository
) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun setQuery(input: String) = this.query.postValue(input)

    val results: LiveData<Resource<List<ExerciseSearchSuggestion>>> = Transformations
        .switchMap(query) { search ->
            if (search.isNullOrBlank()) {
                AbsentLiveData.create()
            } else {
                exerciseSearchSuggestionRepository.search(search)
            }
        }
}
