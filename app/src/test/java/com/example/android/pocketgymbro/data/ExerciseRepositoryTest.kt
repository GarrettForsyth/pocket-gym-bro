package com.example.android.pocketgymbro.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.android.pocketgymbro.api.WorkoutManagerService
import com.example.android.pocketgymbro.db.ExerciseSearchSuggestionDao
import com.example.android.pocketgymbro.db.PocketGymBroDatabase
import com.example.android.pocketgymbro.testing.utils.TestUtils
import com.example.android.pocketgymbro.testing.api.ApiUtil
import com.example.android.pocketgymbro.util.InstantAppExecutors
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class ExerciseSearchSuggestionRepositoryTest {


    private lateinit var exerciseSearchSuggestionRepository: ExerciseSearchSuggestionRepository

    private val workOutManagerService = mock(WorkoutManagerService::class.java)
    private val exerciseSuggestionDao: ExerciseSearchSuggestionDao
            = mock(ExerciseSearchSuggestionDao::class.java)

    val TEST_QUERY = "test query"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(PocketGymBroDatabase::class.java)
        `when`(db.exerciseSearchSuggestionDao()).thenReturn(exerciseSuggestionDao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        exerciseSearchSuggestionRepository = ExerciseSearchSuggestionRepository(
            InstantAppExecutors(),
            exerciseSuggestionDao,
            workOutManagerService
        )
    }

    @Test
    fun fetch_across_network_only_when_observed() {
        // Mock database with empty response
        val dbData = MutableLiveData<List<ExerciseSearchSuggestion>>()
        `when`(exerciseSuggestionDao.getByName("%$TEST_QUERY%")).thenReturn(dbData)

        // Mock network call with 3 suggestions as response
        val exerciseSearchSuggestions = TestUtils.createListOfExerciseSearchSuggestions(3)
        val exerciseSearchSuggestionResponse =
            TestUtils.createExerciseSearchSuggestionResponse(exerciseSearchSuggestions)
        val call = ApiUtil.successCall(exerciseSearchSuggestionResponse)
        `when`(workOutManagerService.fetchExerciseSearchSuggestionByName(TEST_QUERY))
            .thenReturn(call)

        // execute the search without any observers
        val data = exerciseSearchSuggestionRepository.search(TEST_QUERY)
        verify(exerciseSuggestionDao).getByName("%$TEST_QUERY%")
        verifyNoMoreInteractions(workOutManagerService)

        // mock observer
        @Suppress("UNCHECKED_CAST") val observer: Observer<Resource<List<ExerciseSearchSuggestion>>>
                = mock(Observer::class.java) as Observer<Resource<List<ExerciseSearchSuggestion>>>
        data.observeForever(observer)

        // verify still no network call and resource status is set to loading
        verifyNoMoreInteractions(workOutManagerService)
        verify(observer).onChanged(Resource.loading(null))

        // update data while being observed should fetch from network
        val updatedDbData = MutableLiveData<List<ExerciseSearchSuggestion>>()
        `when`(exerciseSuggestionDao.getByName("%$TEST_QUERY%")).thenReturn(updatedDbData)
        dbData.postValue(null)
        verify(workOutManagerService).fetchExerciseSearchSuggestionByName(TEST_QUERY)
        verify(exerciseSuggestionDao).insert(exerciseSearchSuggestions)

        updatedDbData.postValue(exerciseSearchSuggestions)
        verify(observer).onChanged(Resource.success(exerciseSearchSuggestions))
    }

}