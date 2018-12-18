package com.example.android.pocketgymbro.vo

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class ExerciseSearchSuggestionResponse(
    @SerializedName("suggestions")
    val suggestions: List<ExerciseSearchSuggestion>
)

@Entity(primaryKeys = ["data_id"])
data class ExerciseSearchSuggestion(
    @SerializedName("data")
    @Embedded(prefix = "data_")
    val exerciseData: ExerciseSearchData,

    @SerializedName("value")
    val exerciseValue: String
) {

    data class ExerciseSearchData(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String?,

        @SerializedName("category")
        val category: String?,

        @SerializedName("image")
        val image: String?,

        @SerializedName("image_thumbnail")
        val imageThumbnail: String?
    )

}

