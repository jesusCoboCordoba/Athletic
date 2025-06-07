package com.politecnico.athleticapp.ui.progresstracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast // Importa la clase Toast para mostrar mensajes
import androidx.fragment.app.Fragment
import com.politecnico.athleticapp.databinding.FragmentProgressTrackingBinding

class ProgressTrackingFragment : Fragment() {


    private var _binding: FragmentProgressTrackingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProgressTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addPhotoButton.setOnClickListener {

            Toast.makeText(context, "Add Photo clicked! (Aquí iría la lógica de cámara/galería)", Toast.LENGTH_SHORT).show()
        }

        binding.saveButton.setOnClickListener {

            val weight = binding.weightEditText.text.toString()
            val measurement1 = binding.measurementEditText1.text.toString()
            val measurement2 = binding.measurementEditText2.text.toString()

            if (weight.isNotBlank() && measurement1.isNotBlank() && measurement2.isNotBlank()) {

                val message = "Progress Saved:\nWeight: $weight lb\nMeasurement 1: $measurement1 in\nMeasurement 2: $measurement2 in"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}