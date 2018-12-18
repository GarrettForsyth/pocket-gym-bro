package com.example.android.pocketgymbro.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion

/**
 * Database schema that holds a list of exercises.
 */
@Database(
    entities = [
        ExerciseSearchSuggestion::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(PocketGymBroTypeConverters::class)
abstract class PocketGymBroDatabase : RoomDatabase() {

    abstract fun exerciseSearchSuggestionDao(): ExerciseSearchSuggestionDao

}
