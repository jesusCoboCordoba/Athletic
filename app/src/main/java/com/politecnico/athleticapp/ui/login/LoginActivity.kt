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

        // Servicio de recuperacion
        val recoveryText: TextView = findViewById(R.id.textRecovery)
        recoveryText.setOnClickListener {
            val intent = Intent(this, RecoveryActivity::class.java)
            startActivity(intent)
        }


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
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Si login fue exitoso, verificamos tipo de usuario
                        val uid = auth.currentUser?.uid
                        val dbRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("Usuarios")

                        dbRef.child(uid ?: "").get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val tipo = snapshot.child("tipo").getValue(String::class.java)

                                when (tipo) {
                                    "entrenador" -> {
                                        Toast.makeText(this, "Bienvenido, entrenador", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                    "deportista" -> {
                                        Toast.makeText(this, "Bienvenido, deportista", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                    else -> {
                                        Toast.makeText(this, "Tipo de usuario desconocido", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "Usuario no encontrado en la base de datos", Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }
}