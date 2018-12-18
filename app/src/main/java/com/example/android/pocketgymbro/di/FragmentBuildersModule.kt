package com.example.android.pocketgymbro.di

import com.example.android.pocketgymbro.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment() : SearchFragment
}