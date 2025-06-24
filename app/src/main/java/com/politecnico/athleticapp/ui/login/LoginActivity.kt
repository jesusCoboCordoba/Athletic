package com.politecnico.athleticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.politecnico.athleticapp.R
import com.politecnico.athleticapp.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

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
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user == null || user.uid.isEmpty()) {
                            Toast.makeText(this, "Authentication failed: could not get user ID.", Toast.LENGTH_LONG).show()
                            return@addOnCompleteListener
                        }
                        val uid = user.uid
                        val dbRef = FirebaseDatabase.getInstance().getReference("Users")

                        dbRef.child(uid).get().addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val userType = snapshot.child("type").getValue(String::class.java)

                                when (userType) {
                                    "coach" -> {
                                        Toast.makeText(this, "Welcome, Coach!", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                    "athlete" -> {
                                        Toast.makeText(this, "Welcome, Athlete!", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                    else -> {
                                        Toast.makeText(this, "Unknown user type.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(this, "User data not found.", Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, "Failed to access database.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Authentication failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}