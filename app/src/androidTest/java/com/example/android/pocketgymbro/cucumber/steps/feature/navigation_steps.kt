package com.example.android.pocketgymbro.cucumber.steps.feature

import androidx.test.core.app.ActivityScenario
import androidx.test.runner.AndroidJUnit4
import com.example.android.pocketgymbro.MainActivity
import com.example.android.pocketgymbro.cucumber.steps.World
import org.junit.runner.RunWith
import cucumber.api.java.en.Given
import cucumber.api.java.en.When


@RunWith(AndroidJUnit4::class)
class NavigationSteps(private val world: World) {

    @Given("I have just opened the app")
    fun i_have_just_opened_the_app() {
        world.mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @When("I navigate to the Exercise Explorer")
    fun i_navigate_to_the_Exercise_Explorer() {
    }

}
