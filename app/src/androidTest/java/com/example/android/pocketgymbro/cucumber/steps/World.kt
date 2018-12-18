package com.example.android.pocketgymbro.cucumber.steps

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.pocketgymbro.MainActivity
import com.example.android.pocketgymbro.testing.SingleFragmentActivity
import cucumber.api.java.After
import cucumber.api.java.BeforeStep

/**
 * This class is used to share data between steps.
 * It uses picocontainer to inject data into the step files.
 */
class World {

    val context: Context = ApplicationProvider.getApplicationContext<Context>()

    lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    lateinit var singleFragmentActivityScenario: ActivityScenario<SingleFragmentActivity>

    /** TODO
     *
     *  Issue: Running multiple Cucumber Scenarios that utilize Androidx.test.core's  [ActivityScenario]
     *  cause flaky tests.
     *
     *  This is because the activity launched by [ActivityScenario] is not destroyed at the end of the Cucumber Scenario.
     *  This means in a second Cucumber Scenario that uses [ActivityScenario] to launch the same activity runs the
     *  risk of having it's activity destroyed midway through the test.
     *
     *  According to [ActivityScenario]'s documentation (https://developer.android.com/reference/androidx/test/core/app/ActivityScenario)
     *  It should be possible to move the activity's state to destroyed via #moveToState(Lifecycle.State.Destroyed)
     *  However, the source code does not actually support this.
     *
     *  Instead #moveToState(Livecycle.State.CREATED) is used which moves the activity to it's stopped state.
     *  This allows the activity to be destroyed more quickly and seems to avoid (or at least greatly reduce) the flaky
     *  tests
     *
     *  Of course this is far from ideal. Some possible ways to fix this:
     *
     *      - ensure the latest version of ActivityScenario is being used (maybe the documentation is for a newer version)
     *      - copy the ActivityScenario class and add the #moveToState(Livecycle.State.Destroyed)
     *      - abandon ActivityScenario entirely and use [ActivityTestRule] (this does not work well with cucumber and its
     *        Api is not as nice as [ActivityScenario]
     *
     */

    @After
    fun afterScenario() {
        if (::singleFragmentActivityScenario.isInitialized){
            singleFragmentActivityScenario.moveToState(Lifecycle.State.CREATED)
        }

        if (::mainActivityScenario.isInitialized){
            mainActivityScenario.moveToState(Lifecycle.State.CREATED)
        }
    }

    /* This is a bit of an overkill. If tests become slow, maybe use this 'as needed' */
    @BeforeStep
    fun idle() = InstrumentationRegistry.getInstrumentation().waitForIdleSync()

}