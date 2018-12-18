package com.example.android.pocketgymbro.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.pocketgymbro.testing.OpenForTesting
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion

@Dao
@OpenForTesting
abstract class ExerciseSearchSuggestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(exerciseSearchSuggestion: ExerciseSearchSuggestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(exerciseSearchSuggestions: List<ExerciseSearchSuggestion>)

    @Query("SELECT * from exercisesearchsuggestion WHERE exerciseValue LIKE :name")
    abstract fun getByName(name: String): LiveData<List<ExerciseSearchSuggestion>>


}
