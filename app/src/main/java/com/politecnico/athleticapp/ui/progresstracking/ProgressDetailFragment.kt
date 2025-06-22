package com.politecnico.athleticapp.ui.progresstracking


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.politecnico.athleticapp.databinding.FragmentProgressTrackingGraphicsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProgressDetailFragment : Fragment() {

    private var _binding: FragmentProgressTrackingGraphicsBinding? = null
    private val binding get() = _binding!!

    private val database = Firebase.database
    private val progressRef = database.getReference("progress-tracking")

    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressTrackingGraphicsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDateInView()

        binding.selectedDateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        loadProgressForSelectedDate(calendar.timeInMillis)
    }

    private fun updateDateInView() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.selectedDateTextView.text = dateFormat.format(calendar.time)
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                calendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
                updateDateInView()
                loadProgressForSelectedDate(calendar.timeInMillis)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun loadProgressForSelectedDate(timestamp: Long) {
        binding.progressBar.visibility = View.VISIBLE
        clearDisplayedData()

        val startOfDay = getStartOfDay(timestamp)
        val endOfDay = getEndOfDay(timestamp)

        progressRef.orderByChild("timestamp")
            .startAt(startOfDay.toDouble())
            .endAt(endOfDay.toDouble())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.progressBar.visibility = View.GONE

                    var foundEntry: ProgressEntry? = null

                    for (postSnapshot in snapshot.children) {
                        val entry = postSnapshot.getValue<ProgressEntry>()
                        if (entry != null) {
                            foundEntry = entry
                        }
                    }

                    if (foundEntry != null) {
                        displayProgressData(foundEntry)
                    } else {
                        Toast.makeText(context, "No progress data for this date.", Toast.LENGTH_SHORT).show()
                        clearDisplayedData()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed to load progress: ${error.message}", Toast.LENGTH_LONG).show()
                    clearDisplayedData()
                }
            })
    }

    private fun displayProgressData(entry: ProgressEntry) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        binding.detailDateTextView.text = dateFormat.format(Date(entry.timestamp))
        binding.detailWeightTextView.text = "${entry.weight} lb"
        binding.detailMeasurement1TextView.text = "${entry.measurement1} in"
        binding.detailMeasurement2TextView.text = "${entry.measurement2} in"
        Log.d("ProgressDetail", "Attempting to display image. Image URL: ${entry.imageUrl}")


        if (!entry.imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(entry.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_dialog_alert)
                .into(binding.detailImageView)
            binding.detailImageView.contentDescription = "Progress image for ${dateFormat.format(Date(entry.timestamp))}"
            Log.d("ProgressDetail", "Loading image with Glide: ${entry.imageUrl}")
        } else {
            binding.detailImageView.setImageResource(android.R.drawable.ic_menu_gallery)
            binding.detailImageView.contentDescription = "No image available"
        }
    }

    private fun clearDisplayedData() {
        binding.detailDateTextView.text = "No data"
        binding.detailWeightTextView.text = "N/A"
        binding.detailMeasurement1TextView.text = "N/A"
        binding.detailMeasurement2TextView.text = "N/A"
        binding.detailImageView.setImageResource(android.R.drawable.ic_menu_gallery)
        binding.detailImageView.contentDescription = "No image available"
    }


    private fun getStartOfDay(timestamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        return cal.timeInMillis
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}