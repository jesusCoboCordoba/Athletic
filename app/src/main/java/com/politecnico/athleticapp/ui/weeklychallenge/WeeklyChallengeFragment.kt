package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val workouts = listOf(
            DailyWorkout("MONDAY", "Full body strength", 1, true),
            DailyWorkout("TUESDAY", "Cardio and Abs", 2, false),
            DailyWorkout("WEDNESDAY", "Full body strength", 3, false),
            DailyWorkout("THURSDAY", "Cardio and Abs", 4, false),
            DailyWorkout("FRIDAY", "Full body strength", 5, false)
        )

        binding.dailyWorkoutsRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.dailyWorkoutsRecyclerview.adapter = DailyWorkoutAdapter(workouts)

        binding.workoutProgressRing.progress = 20
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}