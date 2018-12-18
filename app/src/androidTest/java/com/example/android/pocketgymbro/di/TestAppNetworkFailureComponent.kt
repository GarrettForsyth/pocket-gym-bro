package com.example.android.pocketgymbro.di

import android.app.Application
import com.example.android.pocketgymbro.application.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestAppModule::class,
        NetworkFailureModule::class,
        MainActivityModule::class
    ]
)
interface TestAppNetworkFailureComponent : AppComponent {

    fun inject(testApp: TestApp)

    @Component.Builder
    interface Builder {

        fun build(): TestAppNetworkFailureComponent

        @BindsInstance
        fun application(application: Application) : Builder
    }

}
