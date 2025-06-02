package com.politecnico.athleticapp.ui.mealplans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.databinding.FragmentBreakfastDetailBinding

class BreakfastDetailFragment : Fragment() {

    private var _binding: FragmentBreakfastDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakfastDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aquí se configurarán las vistas si es necesario

        // Ocultar el loading indicator
        view.post { (activity as? MainActivity)?.hideLoading() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 