package com.example.android.pocketgymbro.cucumber.steps.annotations

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import cucumber.api.java.After
import cucumber.api.java.Before

/**
 * This tag replicates InstantTaskExecutorRule which is itself unusable because
 * cucumber can't use JUnit test rules.
 */
class NoAsyncArchTag {
    @Before("@NoAsyncArch")
    fun set_to_main_thread() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    @After("@NoAsyncArch")
    fun teardown() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}