package com.politecnico.athleticapp.ui.progresstracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentProgressTrackingGraphicsBinding

class ProgressDetailFragment : Fragment() {

    private var _binding: FragmentProgressTrackingGraphicsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressTrackingGraphicsBinding.inflate(inflater, container, false) // ¡Inflado corregido!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = "2025-06-07"
        val weight = "180 lb"
        val measurement1 = "38 in"
        val measurement2 = "34 in"

        binding.detailDateTextView.text = date
        binding.detailWeightTextView.text = weight
        binding.detailMeasurement1TextView.text = measurement1
        binding.detailMeasurement2TextView.text = measurement2


        binding.detailImageView.setImageResource(android.R.drawable.ic_menu_gallery)
        binding.detailImageView.contentDescription = "Imagen de progreso para $date"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
