package com.politecnico.athleticapp.ui.weeklychallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.ItemDailyWorkoutBinding
import com.politecnico.athleticapp.ui.models.DailyWorkout

class DailyWorkoutAdapter(
    private val workouts: List<DailyWorkout>,
    private val onWorkoutClicked: (DailyWorkout) -> Unit,
    private val onCompletionToggled: (DailyWorkout) -> Unit
) : RecyclerView.Adapter<DailyWorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemDailyWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        holder.bind(workouts[position])
    }

    override fun getItemCount(): Int = workouts.size

    inner class WorkoutViewHolder(private val binding: ItemDailyWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: DailyWorkout) {
            binding.dayName.text = workout.day
            binding.workoutType.text = workout.workoutType
            binding.viewDayLink.text = "View Day ${workout.dayNumber}"
            
            if (workout.isCompleted) {
                binding.dayStatusIcon.setImageResource(R.drawable.ic_check_circle_24)
                binding.dayStatusIcon.setColorFilter(ContextCompat.getColor(itemView.context, R.color.accent_green))
            } else {
                binding.dayStatusIcon.setImageResource(R.drawable.ic_time_24)
                binding.dayStatusIcon.clearColorFilter()
            }

            // Click listener for the entire item to navigate
            itemView.setOnClickListener {
                onWorkoutClicked(workout)
            }
            
            // Click listener for the icon to toggle completion
            binding.dayStatusIcon.setOnClickListener {
                onCompletionToggled(workout)
            }
        }
    }
}