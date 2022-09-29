package com.example.hackeruapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hackeruapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        goToLoginActivityOnClick()

        binding.directToLoginTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToLoginActivityOnClick() {
        binding.continueButtonSignup.setOnClickListener {
            createFirebaseUserWithCredentials()
        }
    }

    private fun createFirebaseUserWithCredentials() {
        val email = email_signup_et.text.toString()
        val password = password_signup_et.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) firebaseAuth.createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_LONG).show()

            } else Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, "Email or Password Cannot be empty!", Toast.LENGTH_SHORT).show()
    }

}
