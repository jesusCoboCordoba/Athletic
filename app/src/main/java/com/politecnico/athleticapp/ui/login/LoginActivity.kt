package com.politecnico.athleticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.MainActivity // Asegúrate de que esta línea esté presente

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de que este sea el archivo correcto

        // Servicio de registro
        val registerText: TextView = findViewById(R.id.textRegister)
        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Obtenemos referencias a los elementos del layout

        val emailEditText: EditText = findViewById(R.id.editTextEmail)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.buttonLogin)

        // Establecemos el comportamiento cuando se presiona el botón de login

        loginButton.setOnClickListener {
            // Aquí iría la lógica de validación de usuario
            // Obtenemos el texto que el usuario escribió en los campos
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString()


            // Validamos que no estén vacíos los campos
            if (email.isEmpty() || password.isEmpty()) {
                // Mostramos un mensaje si falta algún campo
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener // Salimos del botón sin continuar
            }

            // Simulamos una autenticación con datos fijos (esto se reemplazaría con conexión a base de datos)
            if (email == "admin@correo.com" && password == "1234") {
                // Si los datos son correctos, mostramos mensaje y vamos al MainActivity
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java) // Cambiado a MainActivity
                startActivity(intent)
                finish() // Opcional: cierra la actividad de login
            } else {
                // Si los datos no coinciden, mostramos error
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }

        }
    }
}