package com.politecnico.athleticapp.ui.weeklychallenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentRoutineExercisesBinding
import com.politecnico.athleticapp.databinding.ItemExerciseBinding
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat

// Updated data class for exercises
data class ExerciseItem(
    val name: String,
    val description: String, 
    val imageResId: Int,
    val reps: String      // e.g., "3 x 8-12 reps"
)

class RoutineExercisesFragment : Fragment() {

    private var _binding: FragmentRoutineExercisesBinding? = null
    private val binding get() = _binding!!

    private val args: RoutineExercisesFragmentArgs by navArgs()

    private lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Extract a simpler subtitle from the full routine title
        val fullTitle = args.routineTitle
        val subtitle = fullTitle.substringAfter("–").substringBefore("(").trim()
        binding.routineSubtitle.text = subtitle

        setupRecyclerView()
        loadExercisesForRoutine(args.routineTitle)
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter { exercise ->
            val action = RoutineExercisesFragmentDirections.actionRoutineExercisesFragmentToExerciseDetailFragment(
                exerciseName = exercise.name,
                exerciseDescription = exercise.description,
                exerciseImageResId = exercise.imageResId
            )
            findNavController().navigate(action)
        }
        binding.exercisesRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = exerciseAdapter
        }
    }

    private fun loadExercisesForRoutine(routineTitle: String) {
        // Placeholder logic with updated data
        val exercises = when (routineTitle) {
            getString(R.string.routine_a_title_full) -> listOf(
                ExerciseItem("Squats", "Description for Squats", R.drawable.weekly_2, "3 Sets x 12 Reps"),
                ExerciseItem("Dumbbell Row", "Description for Dumbbell Row", R.drawable.fu, "3 Sets x 12 Reps"),
                ExerciseItem("Lunge", "Description for Lunge", R.drawable.weekly_1, "3 Sets x 12 Reps"),
                ExerciseItem("Push-ups", "Description for Push-ups", R.drawable.weekly_3, "3 Sets x 10 Reps")
            )
            getString(R.string.routine_b_title_full) -> listOf(
                ExerciseItem("Machine or dumbbell chest press", "Description for Chest Press", R.drawable.fu, "3 x 10-12 reps"),
                ExerciseItem("Lat pulldowns (high pulley)", "Description for Lat Pulldowns", R.drawable.fu, "3 x 10-12 reps"),
                ExerciseItem("Barbell or dumbbell row (1 arm)", "Description for Row", R.drawable.fu, "3 x 10-12 reps"),
                ExerciseItem("Dumbbell bicep curls", "Description for Bicep Curls", R.drawable.fu, "3 x 10-15 reps"),
                ExerciseItem("Tricep pushdowns (pulley)", "Description for Tricep Pushdowns", R.drawable.fu, "3 x 10-15 reps"),
                ExerciseItem("Crunches on mat", "Description for Crunches", R.drawable.fu, "3 x 15-20 reps")
            )
            getString(R.string.routine_c_title_full) -> listOf(
                ExerciseItem("Leg press", "Description for Leg Press", R.drawable.fu, "3 x 10-15 reps"),
                ExerciseItem("Hamstring curl machine", "Description for Hamstring Curl", R.drawable.fu, "3 x 10-15 reps"),
                ExerciseItem("Walking lunges with dumbbells", "Description for Lunges", R.drawable.fu, "3 x 10-12 reps/leg"),
                ExerciseItem("Standing calf raises", "Description for Calf Raises", R.drawable.fu, "3 x 15-20 reps"),
                ExerciseItem("Ab machine or mat crunches", "Description for Ab Crunches", R.drawable.fu, "3 x 15-20 reps"),
                ExerciseItem("Side plank", "Description for Side Plank", R.drawable.fu, "3 x 30s/side")
            )
            else -> emptyList()
        }
        exerciseAdapter.submitList(exercises)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ExerciseAdapter(private val onItemClicked: (ExerciseItem) -> Unit) :
    ListAdapter<ExerciseItem, ExerciseAdapter.ExerciseViewHolder>(ExerciseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = getItem(position)
        holder.bind(exercise)
        holder.itemView.setOnClickListener { onItemClicked(exercise) }
    }

    class ExerciseViewHolder(private val binding: ItemExerciseBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: ExerciseItem) {
            binding.exerciseNameTextview.text = exercise.name
            binding.exerciseRepsTextview.text = exercise.reps
            binding.exerciseListIcon.setImageResource(exercise.imageResId)
        }
    }
}

class ExerciseDiffCallback : DiffUtil.ItemCallback<ExerciseItem>() {
    override fun areItemsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
        return oldItem.name == newItem.name 
    }

    override fun areContentsTheSame(oldItem: ExerciseItem, newItem: ExerciseItem): Boolean {
        return oldItem == newItem
    }
} 