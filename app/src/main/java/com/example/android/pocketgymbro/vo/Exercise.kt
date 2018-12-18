package com.example.android.pocketgymbro.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class Exercise(
    @SerializedName("id")
    val id: Int,

    @SerializedName("license_author")
    val licenseAuthor: String?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("name_original")
    val nameOriginal: String?,

    @SerializedName("creation_date")
    val creationDate: String?,

    @SerializedName("uuid")
    val uuid: String?,

    @SerializedName("license")
    val license: Int?,

    @SerializedName("category")
    val category: Int?,

    @SerializedName("language")
    val language: Int?,

    @SerializedName("muscles")
    val muscles: List<Int>,

    @SerializedName("muscles_secondary")
    val musclesSecondary: List<Int>,

    @SerializedName("equipment")
    val equipment: List<Int>

)
