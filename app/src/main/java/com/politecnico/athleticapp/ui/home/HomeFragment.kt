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
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val cardAnimationDuration = 150L
    private var initialContentAnimated = false

    private fun animateCard(card: View, actionToRunAfter: () -> Unit) {
        val scaleXAnimator = ObjectAnimator.ofFloat(card, View.SCALE_X, defaultScale, zoomedScale, defaultScale)
        val scaleYAnimator = ObjectAnimator.ofFloat(card, View.SCALE_Y, defaultScale, zoomedScale, defaultScale)

        scaleXAnimator.duration = cardAnimationDuration * 2
        scaleYAnimator.duration = cardAnimationDuration * 2

        var actionExecuted = false
        scaleXAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (!actionExecuted) {
                    actionExecuted = true
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
                findNavController().navigate(R.id.action_nav_home_main_to_nav_meal_plans)
            }
        }

        binding.cardCurrentWorkout.setOnClickListener {
            animateCard(it) {
                findNavController().navigate(R.id.action_nav_home_main_to_weeklyChallengeFragment)
            }
        }

        binding.cardTrackProgress.setOnClickListener {
            animateCard(it) {
                findNavController().navigate(R.id.action_nav_home_main_to_nav_progress_tracking)
            }
        }

        binding.cardJoinCommunity.setOnClickListener {
            animateCard(it) {
                Toast.makeText(context, "Join Community Tapped!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.cardWeeklyChallenge.setOnClickListener {
            (activity as? MainActivity)?.showLoading()
            animateCard(it) {
                findNavController().navigate(R.id.action_nav_home_main_to_nav_progress_tracking)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!initialContentAnimated) {
            prepareViewsForEntryAnimation()
            animateFragmentContentIn()
            initialContentAnimated = true
        }
        view.post { (activity as? MainActivity)?.hideLoading() }
    }

    private fun prepareViewsForEntryAnimation() {
        if (_binding == null) return
        binding.userNameGreeting.alpha = 0f
        binding.cardCurrentWorkout.alpha = 0f
        binding.cardTrackProgress.alpha = 0f
        binding.cardJoinCommunity.alpha = 0f
        binding.cardMealPlans.alpha = 0f
        binding.cardWeeklyChallenge.alpha = 0f

        binding.userNameGreeting.translationY = 30f
        binding.cardCurrentWorkout.translationY = 30f
        binding.cardTrackProgress.translationY = 30f
        binding.cardJoinCommunity.translationY = 30f
        binding.cardMealPlans.translationY = 30f
        binding.cardWeeklyChallenge.translationY = 30f
    }

    private fun animateFragmentContentIn() {
        if (_binding == null) return

        val viewsToAnimate = listOf(
            binding.userNameGreeting,
            binding.cardCurrentWorkout,
            binding.cardTrackProgress,
            binding.cardJoinCommunity,
            binding.cardMealPlans,
            binding.cardWeeklyChallenge
        )

        var delay = 50L
        val delayIncrement = 60L
        val entryAnimationDuration = 350L

        for (viewToAnimate in viewsToAnimate) {
            viewToAnimate.postDelayed({
                ObjectAnimator.ofFloat(viewToAnimate, View.ALPHA, 0f, 1f).apply {
                    duration = entryAnimationDuration
                    interpolator = DecelerateInterpolator()
                    start()
                }
                ObjectAnimator.ofFloat(viewToAnimate, View.TRANSLATION_Y, 30f, 0f).apply {
                    duration = entryAnimationDuration
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }, delay)
            delay += delayIncrement
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}