package com.example.android.pocketgymbro.cucumber.steps.annotations

import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.android.pocketgymbro.application.TestApp
import com.example.android.pocketgymbro.di.*
import cucumber.api.java.Before
import org.junit.runner.RunWith

/**
 * A hook that injects the configuration needed for feature test.
 * It's a mark used to differentiate between feature test and integration
 * tests, but also let them share steps.
 */
@RunWith(AndroidJUnit4::class)
class FeatureTag {

    private val registry = InstrumentationRegistry.getInstrumentation()

    // But only for feature test
    @Before("@feature")
    fun this_is_a_feature_test() {
        registry.waitForIdleSync()
        val testApp = ApplicationProvider.getApplicationContext<TestApp>()
        DaggerTestAppComponent
            .builder()
            .application(testApp)
            .build()
            .inject(testApp)
        TestAppInjector.init(testApp)
    }

    @Before("@NetworkFailureFeature")
    fun this_is_a_network_failure_feature_test() {
        registry.waitForIdleSync()
        val testApp = ApplicationProvider.getApplicationContext<TestApp>()
        DaggerTestAppNetworkFailureComponent
            .builder()
            .application(testApp)
            .build()
            .inject(testApp)

        TestAppInjector.init(testApp)
    }

    @Before("@NetworkErrorFeature")
    fun this_is_a_network_error_feature_test() {
        registry.waitForIdleSync()
        val testApp = ApplicationProvider.getApplicationContext<TestApp>()
        DaggerTestAppNetworkErrorComponent
            .builder()
            .application(testApp)
            .build()
            .inject(testApp)

        TestAppInjector.init(testApp)
    }

}