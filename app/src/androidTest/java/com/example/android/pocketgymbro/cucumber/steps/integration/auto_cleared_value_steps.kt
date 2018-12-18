package com.example.android.pocketgymbro.cucumber.steps.integration

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.pocketgymbro.cucumber.steps.World
import com.example.android.pocketgymbro.testing.SingleFragmentActivity
import com.example.android.pocketgymbro.util.autoCleared
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import cucumber.api.java.en.Given
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import java.lang.IllegalStateException


class auto_cleared_value_steps(private val world: World) {

    private val TEST_VALUE: String = "test value"
    private var exception_has_occured = false

    private lateinit var testFragment: TestFragment
    private lateinit var dialogFragment: DialogFragment

    class TestFragment() : Fragment() {
        var value by autoCleared<String>()
    }

    @Given("a fragment with an autoCleared value")
    fun a_fragment_with_an_autoCleared_value() {
        exception_has_occured = false
        testFragment = TestFragment() // new test fragment each time

        world.singleFragmentActivityScenario = ActivityScenario.launch(SingleFragmentActivity::class.java)
            .onActivity { it.setFragment(testFragment) }
    }

    @Given("a value set to {string}")
    fun a_value_set_to(value: String) {
        testFragment.value = TEST_VALUE
    }

    @Given("a dialog fragment is shown")
    fun a_dialog_fragment() {
        dialogFragment = DialogFragment()
        dialogFragment.show(testFragment.fragmentManager!!, "dialog")
    }

    @When("the fragment is replaced with a new instance")
    fun the_fragment_is_replaced_with_a_new_instance() {
        world.singleFragmentActivityScenario?.onActivity {
            it.replaceFragment(TestFragment())
        }
    }

    @When("the value is accessed")
    fun the_value_is_accessed() {
        try {
            testFragment.value
        } catch (e: IllegalStateException) {
            exception_has_occured = true
        }
    }

    @When("it is given a child fragment")
    fun it_is_given_a_child_fragment() {
        testFragment.childFragmentManager.beginTransaction()
            .add(Fragment(), "child fragment")
            .commit()
    }

    @When("the dialog fragment is dismissed")
    fun the_dialog_fragment_is_dismissed() {
        dialogFragment.dismiss()
    }

    @Then("an exception is thrown")
    fun an_exception_is_thrown() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        assertTrue(exception_has_occured)
    }

    @Then("the autocleared value is not cleared")
    fun the_autocleared_value_is_not_cleared() {
        assertThat(testFragment.value, `is`(TEST_VALUE))
    }

}
