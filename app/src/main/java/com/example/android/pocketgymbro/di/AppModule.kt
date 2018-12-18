package com.example.android.pocketgymbro.di

import android.app.Application
import androidx.room.Room
import com.example.android.pocketgymbro.AppExecutors
import com.example.android.pocketgymbro.api.WorkoutManagerService
import com.example.android.pocketgymbro.db.ExerciseSearchSuggestionDao
import com.example.android.pocketgymbro.db.PocketGymBroDatabase
import com.example.android.pocketgymbro.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes =  [ ViewModelModule::class ])
class AppModule {

    @Singleton
    @Provides
    fun provideAppExecutors() : AppExecutors {
        return AppExecutors()
    }

    @Singleton
    @Provides
    fun provideWorkoutManagerService(): WorkoutManagerService {
        return Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WorkoutManagerService::class.java)
    }

    @Singleton
    @Provides
    fun provideExerciseSearchSuggestionDao(db: PocketGymBroDatabase) : ExerciseSearchSuggestionDao {
        return db.exerciseSearchSuggestionDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application) : PocketGymBroDatabase {
        return Room
            .databaseBuilder(app, PocketGymBroDatabase::class.java, "pocketgymbro.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}