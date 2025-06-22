package com.politecnico.athleticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.politecnico.athleticapp.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var radioGroupTipo: RadioGroup
    private lateinit var buttonRegister: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Bind views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        radioGroupTipo = findViewById(R.id.radioGroupTipo)
        buttonRegister = findViewById(R.id.buttonRegister)
        progressBar = findViewById(R.id.progressBar)

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val selectedRadioId = radioGroupTipo.checkedRadioButtonId

            if (email.isEmpty() || password.isEmpty() || selectedRadioId == -1) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            buttonRegister.isEnabled = false

            val tipo = when (selectedRadioId) {
                R.id.radioEntrenador -> "coach"
                R.id.radioDeportista -> "athlete"
                else -> ""
            }

            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    buttonRegister.isEnabled = true

                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: ""
                        val userRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)

                        val userData = mapOf(
                            "email" to email,
                            "type" to tipo
                        )

                        userRef.setValue(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful! You can now log in.", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error saving user data.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

