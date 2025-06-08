package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.databinding.FragmentRoutineExercisesBinding
import com.politecnico.athleticapp.ui.models.RoutineDataProvider

class RoutineExercisesFragment : Fragment() {

    private var _binding: FragmentRoutineExercisesBinding? = null
    private val binding get() = _binding!!
    private val args: RoutineExercisesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        (activity as? MainActivity)?.hideLoading()

        binding.routineDay.text = args.routineDay.uppercase()
        binding.routineName.text = args.routineName.uppercase()
        binding.routineSubtitle.text = args.routineSubtitle

        val exercises = if (args.routineName.equals("Challenge", ignoreCase = true)) {
            RoutineDataProvider.getChallengeExercises()
        } else {
            RoutineDataProvider.getRoutineAExercises()
        }

        val adapter = ExerciseAdapter { exercise ->
            val action = RoutineExercisesFragmentDirections.actionRoutineExercisesFragmentToExerciseDetailFragment(exercise.name)
            findNavController().navigate(action)
        }
        binding.exercisesRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.exercisesRecyclerview.adapter = adapter
        adapter.submitList(exercises)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
} 