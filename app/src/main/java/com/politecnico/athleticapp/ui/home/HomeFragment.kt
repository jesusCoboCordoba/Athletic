package com.politecnico.athleticapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar OnClickListener para la CardView de Meal Plans
        binding.cardMealPlans.setOnClickListener {
            findNavController().navigate(R.id.nav_meal_plans)
        }

        // Aquí podrías añadir listeners para las otras tarjetas si es necesario:
        // binding.cardCurrentWorkout.setOnClickListener { ... }
        // binding.cardTrackProgress.setOnClickListener { ... }
        // binding.cardJoinCommunity.setOnClickListener { ... }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Es una buena práctica configurar listeners en onViewCreated
        // si no dependen de vistas que se inflan directamente en onCreateView
        // pero en este caso, como usamos ViewBinding, binding.cardMealPlans está disponible
        // después de inflar el binding, por lo que onCreateView también es válido.
        // Mantendremos el listener en onCreateView por simplicidad en este ejemplo,
        // pero considera moverlo aquí si la lógica se vuelve más compleja.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}