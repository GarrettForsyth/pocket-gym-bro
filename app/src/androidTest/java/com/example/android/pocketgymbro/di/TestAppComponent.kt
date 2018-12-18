package com.example.android.pocketgymbro.di

import android.app.Application
import android.os.Build.VERSION_CODES.N
import com.example.android.pocketgymbro.application.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * This Dagger Component defines the configuration for feature tests.
 * It will swap out dependencies that are unreliable or slow with fakes.
 *
 * Integration tests will have NOT use this configuration, but rather have
 * all their dependencies left uninitialized so they may be mocked.
 *
 * Cucumber tests using this configuration will be tagged @feature see [feature_test_injection.kt].
 */

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
        PerfectNetworkModule::class,
        MainActivityModule::class
    ]
)
interface TestAppComponent : AppComponent {

    fun inject(testApp: TestApp)

    @Component.Builder
    interface Builder {

        fun build(): TestAppComponent

        @BindsInstance
        fun application(application: Application) : TestAppComponent.Builder
    }

}