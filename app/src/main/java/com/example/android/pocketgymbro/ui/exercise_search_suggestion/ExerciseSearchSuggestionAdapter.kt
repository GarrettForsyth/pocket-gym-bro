package com.example.android.pocketgymbro.ui.exercise_search_suggestion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.example.android.pocketgymbro.AppExecutors
import com.example.android.pocketgymbro.R
import com.example.android.pocketgymbro.databinding.ExerciseSearchSuggestionItemBinding
import com.example.android.pocketgymbro.ui.common.DataBoundListAdapter
import com.example.android.pocketgymbro.vo.ExerciseSearchSuggestion

class ExerciseSearchSuggestionAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors
) : DataBoundListAdapter<ExerciseSearchSuggestion, ExerciseSearchSuggestionItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ExerciseSearchSuggestion>() {
        override fun areItemsTheSame(oldItem: ExerciseSearchSuggestion, newItem: ExerciseSearchSuggestion): Boolean {
            return oldItem.exerciseData.id == newItem.exerciseData.id
        }

        override fun areContentsTheSame(oldItem: ExerciseSearchSuggestion, newItem: ExerciseSearchSuggestion): Boolean {
            return oldItem.exerciseValue == newItem.exerciseValue
            && oldItem.exerciseData.image == newItem.exerciseData.image
        }
    }

) {

    override fun createBinding(parent: ViewGroup): ExerciseSearchSuggestionItemBinding {
        return DataBindingUtil.inflate<ExerciseSearchSuggestionItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.exercise_search_suggestion_item,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: ExerciseSearchSuggestionItemBinding, item: ExerciseSearchSuggestion) {
        binding.exerciseSearchSuggestion = item
    }
}