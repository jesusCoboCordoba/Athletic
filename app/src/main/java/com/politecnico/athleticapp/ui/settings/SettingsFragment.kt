package com.politecnico.athleticapp.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.politecnico.athleticapp.databinding.FragmentSettingsBinding
import com.politecnico.athleticapp.ui.login.LoginActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val PREFS_NAME = "athletic_app_settings"
    private val KEY_NOTIFICATIONS = "notifications_enabled"
    private val KEY_DARK_MODE = "dark_mode_enabled"
    private val KEY_UNITS = "units_selected"

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val notificationsEnabled = sharedPrefs.getBoolean(KEY_NOTIFICATIONS, true)
        binding.notificationsSwitch.isChecked = notificationsEnabled
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
            val message = if (isChecked) "Notifications ON" else "Notifications OFF"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        val darkModeEnabled = sharedPrefs.getBoolean(KEY_DARK_MODE, false)
        binding.darkModeSwitch.isChecked = darkModeEnabled
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
            val message = if (isChecked) "Dark Mode ON" else "Dark Mode OFF"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        val unitsSelected = sharedPrefs.getString(KEY_UNITS, "imperial")
        when (unitsSelected) {
            "imperial" -> binding.imperialRadioButton.isChecked = true
            "metric" -> binding.metricRadioButton.isChecked = true
        }
        binding.unitsRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.imperialRadioButton.id -> {
                    sharedPrefs.edit().putString(KEY_UNITS, "imperial").apply()
                    Toast.makeText(context, "Units set to Imperial", Toast.LENGTH_SHORT).show()
                }
                binding.metricRadioButton.id -> {
                    sharedPrefs.edit().putString(KEY_UNITS, "metric").apply()
                    Toast.makeText(context, "Units set to Metric", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.logoutButton.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout() {
        firebaseAuth.signOut()

        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
        Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}