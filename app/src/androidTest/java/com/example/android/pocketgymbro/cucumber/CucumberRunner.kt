package com.example.android.pocketgymbro.cucumber

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner
import com.example.android.pocketgymbro.application.TestApp
import cucumber.api.android.CucumberInstrumentationCore

/**
 * This class must be in a different package than the glue code
 * (this class is in '...cucumber' and glue is in '...cucumber.steps')
 */

class CucumberRunner : AndroidJUnitRunner() {

    private val instrumentationCore = CucumberInstrumentationCore(this)

    /**
     * Inject the TestApp to use the test configurations provided by dagger.
     */
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        instrumentationCore.create(arguments)
    }

    override fun onStart() {
        waitForIdleSync()
        instrumentationCore.start()
    }
}