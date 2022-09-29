package com.example.hackeruapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hackeruapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        onButtonCLick()

        binding.directSignupTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
    }

    private fun onButtonCLick() {
        binding.conrinueLogin.setOnClickListener {
            loginFireBaseUserWithCredentials()
        }
    }

    private fun loginFireBaseUserWithCredentials() {
        val email = email_login_et.text.toString()
        val password = password_login_et.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Email or Password Cannot be empty!", Toast.LENGTH_SHORT).show()
    }
}