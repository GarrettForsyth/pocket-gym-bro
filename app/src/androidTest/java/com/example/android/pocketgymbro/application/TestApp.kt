package com.example.android.pocketgymbro.application

import android.app.Activity
import android.app.Application
import android.util.Log
import com.example.android.pocketgymbro.di.AppInjector
import com.example.android.pocketgymbro.di.TestAppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Separate app for tests to prevent initializing dependency injection.
 */
class TestApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
    }
}
