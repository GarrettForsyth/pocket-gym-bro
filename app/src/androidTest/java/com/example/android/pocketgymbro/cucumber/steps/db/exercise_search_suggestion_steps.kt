package com.example.android.pocketgymbro.cucumber.steps.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android.pocketgymbro.db.ExerciseSearchSuggestionDao
import com.example.android.pocketgymbro.db.PocketGymBroDatabase
import com.example.android.pocketgymbro.testing.utils.TestUtils
import com.example.android.pocketgymbro.util.getValueBlocking
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.When
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import java.io.IOException

class ExerciseSearchSuggestionSteps() {

    private lateinit var db: PocketGymBroDatabase
    private lateinit var exerciseSearchSuggestionDao: ExerciseSearchSuggestionDao

    private lateinit var exerciseSearchSuggestion: ExerciseSearchSuggestion
    private lateinit var listOfExerciseSearchSuggestions: List<ExerciseSearchSuggestion>

    private val UPDATED_NAME = "updated test exercise_search_suggestion"
    private val NAME = "test exercise_search_suggestion"

    @Before
    fun before_a_db_test() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PocketGymBroDatabase::class.java).build()
        exerciseSearchSuggestionDao = db.exerciseSearchSuggestionDao()
    }

    @After
    @Throws(IOException::class)
    fun after_a_db_test(){
        db.close()
    }

    @Given("an ExerciseSearchSuggestion")
    fun an_ExerciseSearchSuggestion() {
        exerciseSearchSuggestion = TestUtils.createExerciseSearchSuggestion(
            id = 1, name = NAME
        )
    }

    @When("insert another ExerciseSearchSuggestion with the same id and different values")
    fun insert_another_ExerciseSearchSuggestion_with_the_same_id_and_different_values() {
        val updatedExerciseSearchSuggestion = TestUtils.createExerciseSearchSuggestion(
            id = 1, name =  UPDATED_NAME
        )
        exerciseSearchSuggestionDao.insert(updatedExerciseSearchSuggestion)
    }

    @When("I insert the ExerciseSearchSuggestion into the database")
    fun i_insert_the_ExerciseSearchSuggestion_into_the_database() {
        exerciseSearchSuggestionDao.insert(exerciseSearchSuggestion)
    }

    @Given("a list of ExerciseSearchSuggestions")
    fun a_list_of_ExerciseSearchSuggestions() {
        listOfExerciseSearchSuggestions =
                TestUtils.createListOfExerciseSearchSuggestions(5)
    }

    @When("I insert the list of ExerciseSearchSuggestions into the database")
    fun i_insert_the_list_of_ExerciseSearchSuggestions_into_the_database() {
        exerciseSearchSuggestionDao.insert(listOfExerciseSearchSuggestions)
    }

    @When("I query {string} from ExerciseSearchSuggestions")
    fun i_query_from_ExerciseSearchSuggestions(name: String) {
        listOfExerciseSearchSuggestions =
            exerciseSearchSuggestionDao.getByName(name)
                .getValueBlocking()
    }

    @Then("no errors occur")
    fun no_errors_occur() {
    }

    @Then("I get a result with {int} of entries")
    fun i_get_a_result_with_of_entries(expectedSize: Int?) {
        assertThat(listOfExerciseSearchSuggestions.size,
            equalTo(expectedSize))
    }

   @Then("the new old ExerciseSearchSuggestion is replaced with the new one")
    fun the_new_old_ExerciseSearchSuggestion_is_replaced_with_the_new_one() {
        assertThat(exerciseSearchSuggestionDao.getByName(
            UPDATED_NAME).getValueBlocking().size, equalTo(1))
        assertThat(exerciseSearchSuggestionDao.getByName(
            NAME).getValueBlocking().size, equalTo(0))
    }

}
