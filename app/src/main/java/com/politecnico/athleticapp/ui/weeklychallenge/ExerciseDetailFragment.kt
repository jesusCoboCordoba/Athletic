package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.politecnico.athleticapp.databinding.FragmentExerciseDetailBinding
import com.politecnico.athleticapp.ui.models.RoutineDataProvider

class ExerciseDetailFragment : Fragment() {

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        val exerciseName = args.exerciseName
        val exercise = RoutineDataProvider.getExerciseByName(exerciseName)

        exercise?.let {
            binding.exerciseDetailName.text = it.name
            binding.exerciseInstruction.text = it.description
            binding.exerciseDetailImage.setImageResource(it.imageResId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
} 