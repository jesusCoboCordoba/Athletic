package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentWeeklyChallengeBinding

class WeeklyChallengeFragment : Fragment() {

    private var _binding: FragmentWeeklyChallengeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeeklyChallengeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeeklyChallengeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(WeeklyChallengeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial state: show loading, hide content
        binding.loadingProgressbar.visibility = View.VISIBLE
        binding.contentScrollView.visibility = View.GONE

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loadingProgressbar.visibility = View.VISIBLE
                binding.contentScrollView.visibility = View.GONE
            } else {
                binding.loadingProgressbar.visibility = View.GONE
                binding.contentScrollView.visibility = View.VISIBLE
                (activity as? MainActivity)?.hideLoading()
            }
        }

        viewModel.loadChallengeData() // Start loading data

        // Navigation listeners for routine cards
        binding.cardRoutineA.setOnClickListener {
            val action = WeeklyChallengeFragmentDirections.actionWeeklyChallengeFragmentToRoutineExercisesFragment(
                routineTitle = getString(R.string.routine_a_title_full),
                imageResId = R.drawable.group_1
            )
            findNavController().navigate(action)
        }

        binding.cardRoutineB.setOnClickListener {
            val action = WeeklyChallengeFragmentDirections.actionWeeklyChallengeFragmentToRoutineExercisesFragment(
                routineTitle = getString(R.string.routine_b_title_full),
                imageResId = R.drawable.group_2
            )
            findNavController().navigate(action)
        }

        binding.cardRoutineC.setOnClickListener {
            val action = WeeklyChallengeFragmentDirections.actionWeeklyChallengeFragmentToRoutineExercisesFragment(
                routineTitle = getString(R.string.routine_c_title_full),
                imageResId = R.drawable.group_3
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 