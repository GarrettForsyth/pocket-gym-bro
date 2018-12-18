package com.example.android.pocketgymbro.di

import android.app.Application
import androidx.room.Room
import com.example.android.pocketgymbro.db.ExerciseSearchSuggestionDao
import com.example.android.pocketgymbro.db.PocketGymBroDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class TestAppModule {

    @Singleton
    @Provides
    fun provideExerciseSearchSuggestionDao(db: PocketGymBroDatabase): ExerciseSearchSuggestionDao {
        return db.exerciseSearchSuggestionDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): PocketGymBroDatabase {
        return Room
            .inMemoryDatabaseBuilder(app, PocketGymBroDatabase::class.java)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

}