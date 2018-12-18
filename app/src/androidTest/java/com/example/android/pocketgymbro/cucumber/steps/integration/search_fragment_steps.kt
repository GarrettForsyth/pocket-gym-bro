package com.example.android.pocketgymbro.cucumber.steps.integration

import android.util.Log
import androidx.lifecycle.*
import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import com.example.android.pocketgymbro.AppExecutors
import com.example.android.pocketgymbro.R
import com.example.android.pocketgymbro.testing.SingleFragmentActivity
import com.example.android.pocketgymbro.cucumber.steps.World
import com.example.android.pocketgymbro.testing.utils.TestUtils
import com.example.android.pocketgymbro.ui.search.SearchFragment
import com.example.android.pocketgymbro.ui.search.SearchViewModel
import com.example.android.pocketgymbro.util.ViewModelUtil
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import com.example.android.pocketgymbro.vo.Resource
import cucumber.api.java.en.Then
import org.junit.runner.RunWith
import cucumber.api.java.en.Given
import cucumber.api.java.en.When
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4::class)
class SearchFragmentSteps(private val world: World) {

    private val searchFragment = SearchFragment()
    private val searchViewModelMock = mock(SearchViewModel::class.java)
    private val results = MediatorLiveData<Resource<List<ExerciseSearchSuggestion>>>()


    @Given("a search fragment")
    fun a_search_fragment() {
        world.singleFragmentActivityScenario = ActivityScenario.launch(SingleFragmentActivity::class.java)
            .onActivity { activity ->
                Log.d("mytesttag", "a_search_fragment: onActivity")
                searchFragment.apply {
                    viewModelFactory = ViewModelUtil.createFor(searchViewModelMock)
                    appExecutors = AppExecutors()
                    `when`(searchViewModelMock.results).thenReturn(results)
                }
                activity.setFragment(searchFragment)
            }
    }

    @When("a result {string} is found")
    fun a_result_is_found(name: String) {
        results.postValue(
            Resource.success(
                listOf(
                    TestUtils.createExerciseSearchSuggestion(1, name)
                )
            )
        )
    }

    @When("a network failure occurs")
    fun a_network_failure_occurs() {
        results.postValue(
            Resource.error(
                world.context.getString(R.string.internet_unavailable),
                null
            )
        )
    }

    @Then("it should query the viewmodel for {string}")
    fun it_should_query_the_viewmodel_for(query: String) {
        verify(searchViewModelMock, atLeastOnce()).setQuery(query)
    }

}