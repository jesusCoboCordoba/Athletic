package com.politecnico.athleticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.MainActivity // Asegúrate de que esta línea esté presente

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de que este sea el archivo correcto

        val loginButton: Button = findViewById(R.id.buttonLogin)
        loginButton.setOnClickListener {
            // Aquí iría la lógica de validación de usuario
            val intent = Intent(this, MainActivity::class.java) // Cambiado a MainActivity
            startActivity(intent)
            finish() // Opcional: cierra la actividad de login
        }
    }
}