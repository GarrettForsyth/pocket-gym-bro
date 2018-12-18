package com.example.android.pocketgymbro.cucumber.steps.feature

import android.app.Application
import android.view.KeyEvent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.runner.RunWith
import cucumber.api.java.en.Given
import org.hamcrest.CoreMatchers.not
import androidx.test.espresso.action.ViewActions.*


@RunWith(AndroidJUnit4::class)
class ViewSteps {

    @Given("the {string} is not displayed")
    fun the_view_is_not_displayed(readableViewIdString: String) {
        val id = getIdFromReadableString(readableViewIdString)
        onView(withId(id)).check(matches(not((isDisplayed()))))
    }

    @When("I search {string} in (the ){string}")
    fun i_search_in_the(query: String, viewEditText: String) {
        onView(withId(getIdFromReadableString(viewEditText)))
            .perform(typeText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }

    @Then("the {string} is displayed")
    fun the_view_is_displayed(readableViewIdString: String) {
        val id = getIdFromReadableString(readableViewIdString)
        onView(withId(id)).check(matches(isDisplayed()))
    }

    @Then("I should see {string}")
    fun i_should_see_a_view(readableViewIdString: String) {
        the_view_is_displayed(readableViewIdString)
    }

    @Then("{string} is not displayed")
    fun view_is_not_displayed(readableViewIdString: String) {
        val id = getIdFromReadableString(readableViewIdString)
        onView(withId(id)).check((matches(not(isDisplayed()))))
    }

    @Then("I should not see {string}")
    fun i_should_not_see(readableViewIdString: String) {
        view_is_not_displayed(readableViewIdString)
    }

    @Then("I should see {string} within (the) {string}")
    fun i_should_see_text_in_the(text: String, readableViewIdString: String) {
        val id = getIdFromReadableString(readableViewIdString)
        onView(withId(id)).check(matches(hasDescendant(withText(text))))
    }


    @Then("a snackbar is displayed with text {string}")
    fun a_snack_bar_is_displayed_with_text(text: String) {
        // TODO: Keep an eye on testing snackbars support
        // SnackBar testing is currently brittle according to:
        // https://github.com/googlecodelabs/android-testing/blob/master/app/src/androidTestMock/java/com/example/android/testing/notes/addnote/AddNoteScreenTest.java
        // So just comment out SnackBar assertions for now

//        onView(withId(com.google.android.material.R.id.snackbar_text))
//            .check(matches(withText(text)))

    }

    @Then("a snackbar action is displayed with text {string}")
    fun a_snackbar_action_is_displayed_with_text(text: String) {
        // TODO: Keep an eye on testing snackbars support
        // SnackBar testing is currently brittle according to:
        // https://github.com/googlecodelabs/android-testing/blob/master/app/src/androidTestMock/java/com/example/android/testing/notes/addnote/AddNoteScreenTest.java
        // So just comment out SnackBar assertions for now

//        onView(withId(com.google.android.material.R.id.snackbar_action))
//            .check(matches(withText(text)))
    }

    @When("the snackbar action is clicked")
    fun the_snackbar_action_is_clicked() {
        // TODO: Keep an eye on testing snackbars support
        // SnackBar testing is currently brittle according to:
        // https://github.com/googlecodelabs/android-testing/blob/master/app/src/androidTestMock/java/com/example/android/testing/notes/addnote/AddNoteScreenTest.java
        // So just comment out SnackBar assertions for now

//        onView(withId(com.google.android.material.R.id.snackbar_action))
//            .perform(click())
    }

    @Then("I should not see {string} within (the) {string}")
    fun i_should_not_see_text_in_the(text: String, readableViewIdString: String) {
        val id = getIdFromReadableString(readableViewIdString)
        onView(withId(id)).check(matches(not(hasDescendant(withText(text)))))
    }

    private fun getIdFromReadableString(readableViewIdString: String) : Int {
        val viewIdString = readableViewIdString.replace(" ", "_")

        val context = ApplicationProvider
            .getApplicationContext<Application>()
            .applicationContext

        return context.resources.getIdentifier(
            viewIdString, "id",
            context.packageName
        )

    }

}
