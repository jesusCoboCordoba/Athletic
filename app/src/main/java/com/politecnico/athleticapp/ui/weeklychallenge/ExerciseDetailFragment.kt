package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.politecnico.athleticapp.databinding.FragmentExerciseDetailBinding

class ExerciseDetailFragment : Fragment() {

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exerciseDetailName.text = args.exerciseName
        // The description is no longer in the new layout, so we remove it.
        // binding.exerciseDetailDescription.text = args.exerciseDescription 
        
        if (args.exerciseImageResId != 0) {
            binding.exerciseDetailImage.setImageResource(args.exerciseImageResId)
        }
        // The new UI elements like timer, buttons, and progress bar are now in the layout.
        // The logic to make them interactive (e.g., starting a timer) would be added here.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 