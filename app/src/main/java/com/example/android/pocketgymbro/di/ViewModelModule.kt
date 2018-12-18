package com.example.android.pocketgymbro.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.pocketgymbro.ui.search.SearchViewModel
import com.example.android.pocketgymbro.viewmodel.PocketGymBroViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PocketGymBroViewModelFactory) : ViewModelProvider.Factory
}