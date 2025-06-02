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
    val reps: String,      // e.g., "3 x 8-12 reps"
    val restTime: String   // e.g., "Rest: 60s"
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

        binding.routineExercisesTitle.text = args.routineTitle
        if (args.imageResId != 0) {
            binding.routineExercisesImage.setImageResource(args.imageResId)
        }

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
                ExerciseItem("Machine squats or bodyweight squats", "Description for Squats", R.drawable.group_1, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Dumbbell or machine chest press", "Description for Chest Press", R.drawable.group_1, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Machine row or low pulley row", "Description for Row", R.drawable.group_1, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Dumbbell shoulder press", "Description for Shoulder Press", R.drawable.group_1, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Calf raises", "Description for Calf Raises", R.drawable.group_1, "3 x 15-20 reps", "Rest: 45s"),
                ExerciseItem("Plank", "Description for Plank", R.drawable.group_1, "3 x 30-60s", "Rest: 30s")
            )
            getString(R.string.routine_b_title_full) -> listOf(
                ExerciseItem("Machine or dumbbell chest press", "Description for Chest Press", R.drawable.group_2, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Lat pulldowns (high pulley)", "Description for Lat Pulldowns", R.drawable.group_2, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Barbell or dumbbell row (1 arm)", "Description for Row", R.drawable.group_2, "3 x 10-12 reps", "Rest: 60-90s"),
                ExerciseItem("Dumbbell bicep curls", "Description for Bicep Curls", R.drawable.group_2, "3 x 10-15 reps", "Rest: 60s"),
                ExerciseItem("Tricep pushdowns (pulley)", "Description for Tricep Pushdowns", R.drawable.group_2, "3 x 10-15 reps", "Rest: 60s"),
                ExerciseItem("Crunches on mat", "Description for Crunches", R.drawable.group_2, "3 x 15-20 reps", "Rest: 45s")
            )
            getString(R.string.routine_c_title_full) -> listOf(
                ExerciseItem("Leg press", "Description for Leg Press", R.drawable.group_3, "3 x 10-15 reps", "Rest: 60-90s"),
                ExerciseItem("Hamstring curl machine", "Description for Hamstring Curl", R.drawable.group_3, "3 x 10-15 reps", "Rest: 60-90s"),
                ExerciseItem("Walking lunges with dumbbells", "Description for Lunges", R.drawable.group_3, "3 x 10-12 reps/leg", "Rest: 60s"),
                ExerciseItem("Standing calf raises", "Description for Calf Raises", R.drawable.group_3, "3 x 15-20 reps", "Rest: 45s"),
                ExerciseItem("Ab machine or mat crunches", "Description for Ab Crunches", R.drawable.group_3, "3 x 15-20 reps", "Rest: 45s"),
                ExerciseItem("Side plank", "Description for Side Plank", R.drawable.group_3, "3 x 30s/side", "Rest: 30s")
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

            val restPrefix = "REST: "
            val restTextFull = "$restPrefix${exercise.restTime}"
            val spannable = SpannableString(restTextFull)
            val context = binding.root.context

            val blueColor = ContextCompat.getColor(context, android.R.color.holo_blue_dark)
            val grayColor = ContextCompat.getColor(context, android.R.color.darker_gray)

            spannable.setSpan(ForegroundColorSpan(blueColor), 0, restPrefix.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(ForegroundColorSpan(grayColor), restPrefix.length, restTextFull.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            binding.exerciseRestTextview.text = spannable
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