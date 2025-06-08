package com.politecnico.athleticapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.politecnico.athleticapp.databinding.FragmentSettingsBinding // Asegúrate que este nombre de clase sea correcto para tu layout settings_fragment_layout.xml

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val PREFS_NAME = "athletic_app_settings"
    private val KEY_NOTIFICATIONS = "notifications_enabled"
    private val KEY_DARK_MODE = "dark_mode_enabled"
    private val KEY_UNITS = "units_selected"

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

        val notificationsEnabled = sharedPrefs.getBoolean(KEY_NOTIFICATIONS, true) // Valor por defecto: true
        binding.notificationsSwitch.isChecked = notificationsEnabled


        val darkModeEnabled = sharedPrefs.getBoolean(KEY_DARK_MODE, false) // Valor por defecto: false
        binding.darkModeSwitch.isChecked = darkModeEnabled

        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        val unitsSelected = sharedPrefs.getString(KEY_UNITS, "imperial") // Valor por defecto: "imperial"
        when (unitsSelected) {
            "imperial" -> binding.imperialRadioButton.isChecked = true
            "metric" -> binding.metricRadioButton.isChecked = true
        }

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
            val message = if (isChecked) "Notifications ON" else "Notifications OFF"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }


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
            Toast.makeText(context, "Logging out... (Aquí iría la lógica de cierre de sesión)", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
