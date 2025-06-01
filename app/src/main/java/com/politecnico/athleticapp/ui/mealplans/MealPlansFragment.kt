package com.politecnico.athleticapp.ui.mealplans

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentMealPlansBinding

class MealPlansFragment : Fragment() {

    private var _binding: FragmentMealPlansBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.breakfastCard.setOnClickListener {
            findNavController().navigate(R.id.nav_breakfast_detail)
        }

        // Aquí podrías añadir listeners para las otras tarjetas de comida (coffee_break_card, etc.)
        // si quieres que también naveguen a algún sitio.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 