package com.example.android.pocketgymbro.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.android.pocketgymbro.application.TestApp
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * The Test Injector only injects. Building is done elsewhere to allow for different
 * dependencies to be injected.
 */
// TODO: Lots of code duplication here..
object TestAppInjector {
    fun init(testApp: TestApp) {
        testApp
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityPaused(p0: Activity?) {}
                override fun onActivityResumed(p0: Activity?) {}
                override fun onActivityStarted(p0: Activity?) {}
                override fun onActivityDestroyed(p0: Activity?) {}
                override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {}
                override fun onActivityStopped(p0: Activity?) {}

            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentActivityCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true
                )
        }
    }
}