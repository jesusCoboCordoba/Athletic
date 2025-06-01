package com.politecnico.athleticapp.ui.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.politecnico.athleticapp.MainActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val defaultScale = 1.0f
    private val zoomedScale = 1.05f
    private val animationDuration = 150L

    private fun animateCard(card: View, actionToRunAfter: () -> Unit) {
        val scaleXAnimator = ObjectAnimator.ofFloat(card, View.SCALE_X, defaultScale, zoomedScale, defaultScale)
        val scaleYAnimator = ObjectAnimator.ofFloat(card, View.SCALE_Y, defaultScale, zoomedScale, defaultScale)

        scaleXAnimator.duration = animationDuration * 2
        scaleYAnimator.duration = animationDuration * 2

        var actionExecuted = false
        scaleXAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!actionExecuted) {
                    actionExecuted = true
                    // Usar Handler para postear la acción asegura que se ejecuta después del ciclo de la animación
                    // y en el hilo principal, especialmente si la acción involucra navegación.
                    Handler(Looper.getMainLooper()).post(actionToRunAfter)
                }
            }
        })

        card.pivotX = card.width / 2f
        card.pivotY = card.height / 2f
        scaleXAnimator.start()
        scaleYAnimator.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cardMealPlans.setOnClickListener {
            (activity as? MainActivity)?.showLoading()
            animateCard(it) {
                // No necesitas el postDelayed aquí si la navegación es rápida
                // o si el loading indicator es suficiente.
                // Si la navegación es a una pantalla que carga datos, el hideLoading() debería estar allí.
                findNavController().navigate(R.id.action_nav_home_main_to_nav_meal_plans)
                // Considera llamar a hideLoading() en el destino, por ejemplo en MealPlansFragment.onViewCreated
                // o después de un retraso si es solo para simulación:
                 Handler(Looper.getMainLooper()).postDelayed({ (activity as? MainActivity)?.hideLoading() }, 300) // Pequeño retraso para ver el loader
            }
        }

        binding.cardCurrentWorkout.setOnClickListener {
            // (activity as? MainActivity)?.showLoading() // Ejemplo si se quisiera loading
            animateCard(it) {
                // findNavController().navigate(R.id.alguna_accion_workout)
                Toast.makeText(context, "Current Workout Tapped!", Toast.LENGTH_SHORT).show()
                // (activity as? MainActivity)?.hideLoading() // Ejemplo
            }
        }

        binding.cardTrackProgress.setOnClickListener {
            animateCard(it) {
                Toast.makeText(context, "Track Progress Tapped!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardJoinCommunity.setOnClickListener {
            animateCard(it) {
                Toast.makeText(context, "Join Community Tapped!", Toast.LENGTH_SHORT).show()
            }
        }

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