package com.politecnico.athleticapp.ui.weeklychallenge

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentWeeklyChallengeBinding
import com.politecnico.athleticapp.databinding.ItemDailyWorkoutBinding
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.ui.models.DailyWorkout

class WeeklyChallengeFragment : Fragment() {

    private var _binding: FragmentWeeklyChallengeBinding? = null
    private val binding get() = _binding!!

    private lateinit var workouts: MutableList<DailyWorkout>
    private lateinit var dailyWorkoutAdapter: DailyWorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyChallengeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        (activity as? MainActivity)?.hideLoading()

        setupWorkouts()
        setupRecyclerView()
        updateProgress()
    }

    private fun setupWorkouts() {
        // Using a mutable list to allow state changes
        workouts = mutableListOf(
            DailyWorkout("MONDAY", "Full body strength", 1, true),
            DailyWorkout("TUESDAY", "Cardio and Abs", 2, false),
            DailyWorkout("WEDNESDAY", "Full body strength", 3, false),
            DailyWorkout("THURSDAY", "Cardio and Abs", 4, false),
            DailyWorkout("FRIDAY", "Full body strength", 5, false)
        )
    }

    private fun setupRecyclerView() {
        dailyWorkoutAdapter = DailyWorkoutAdapter(
            workouts,
            onWorkoutClicked = { workout ->
                val action = WeeklyChallengeFragmentDirections.actionWeeklyChallengeFragmentToRoutineExercisesFragment(
                    routineName = "Workout Plan",
                    routineDay = "Day ${workout.dayNumber}",
                    routineSubtitle = workout.workoutType
                )
                findNavController().navigate(action)
            },
            onCompletionToggled = { workout ->
                workout.isCompleted = !workout.isCompleted
                updateProgress()
                dailyWorkoutAdapter.notifyDataSetChanged() // To update the icon
            }
        )
        binding.dailyWorkoutsRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dailyWorkoutsRecyclerview.adapter = dailyWorkoutAdapter
    }

    private fun updateProgress() {
        val completedWorkouts = workouts.count { it.isCompleted }
        val totalWorkouts = workouts.size
        val progressPercentage = if (totalWorkouts > 0) (completedWorkouts * 100 / totalWorkouts) else 0

        binding.progressText.text = "$completedWorkouts/$totalWorkouts Workouts"
        
        // Animate the progress ring
        val currentProgress = binding.workoutProgressRing.progress
        val progressAnimator = ObjectAnimator.ofInt(binding.workoutProgressRing, "progress", currentProgress, progressPercentage)
        progressAnimator.duration = 500 // Animation duration in milliseconds
        progressAnimator.interpolator = DecelerateInterpolator()
        progressAnimator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}