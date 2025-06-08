package com.politecnico.athleticapp.ui.weeklychallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.politecnico.athleticapp.databinding.ItemExerciseBinding
import com.politecnico.athleticapp.ui.models.Exercise

class ExerciseAdapter(private val onItemClicked: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise, onItemClicked)
    }

    class ExerciseViewHolder(private val binding: ItemExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise, onItemClicked: (Exercise) -> Unit) {
            binding.exerciseNameTextview.text = exercise.name
            binding.exerciseRepsTextview.text = exercise.setsAndReps
            binding.exerciseListIcon.setImageResource(exercise.imageResId)
            binding.startButton.setOnClickListener { onItemClicked(exercise) }
        }
    }
}

class ExerciseDiffCallback : DiffUtil.ItemCallback<Exercise>() {
    override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
        return oldItem == newItem
    }
} 