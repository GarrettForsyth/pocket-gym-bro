package com.example.android.pocketgymbro.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.pocketgymbro.data.ExerciseSearchSuggestionRepository
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class SearchViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var searchViewModel: SearchViewModel
    private val repository = mock(ExerciseSearchSuggestionRepository::class.java)

    @Before
    fun init(){
        // Needs to be created after instantTaskExecutor rule is run
        searchViewModel = SearchViewModel(repository)
    }

    @Test
    fun search_queries_repository() {
        val result = mock(Observer::class.java)
        @Suppress("UNCHECKED_CAST")
        searchViewModel.results.observeForever(result as Observer<in Resource<List<ExerciseSearchSuggestion>>>)
        searchViewModel.setQuery("test query")
        verify(repository).search("test query")
    }

    @Test
    fun empty() {
        @Suppress("UNCHECKED_CAST")
        val result: Observer<Resource<List<ExerciseSearchSuggestion>>>
            = mock(Observer::class.java) as Observer<Resource<List<ExerciseSearchSuggestion>>>

        searchViewModel.results.observeForever(result)
        verifyNoMoreInteractions(repository)
    }

}