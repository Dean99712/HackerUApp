package com.example.hackeruapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.hackeruapp.R
import com.example.hackeruapp.viewmodel.RegistrationViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUpFragment : Fragment() {

    private lateinit var googleGetContent: ActivityResultLauncher<Intent>
    private val registrationViewModel: RegistrationViewModel by activityViewModels()
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        googleGetContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { content ->
                onGoogleIntentResult(content)
            }

        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onStart() {
        super.onStart()

        setOnGoogleSignInClickListener()
    }

    private fun setOnGoogleSignInClickListener() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        val googleIntent =
            GoogleSignIn.getClient(requireActivity(), googleSignInOptions).signInIntent
        google_sign_in_button.setOnClickListener {
            googleGetContent.launch(googleIntent)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun onGoogleIntentResult(content: ActivityResult) {
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(content.data)
        task
            .addOnSuccessListener {
                loginOrSignUpToFirebase(it)
                displayToast(" Hey " + it.displayName.toString())
            }
            .addOnFailureListener {
                displayToast("If you cant use GoogleSignIn please sign in the regular way")
            }
    }

    private fun loginOrSignUpToFirebase(googleSignInAccount: GoogleSignInAccount) {
        firebaseAuth.fetchSignInMethodsForEmail(googleSignInAccount.email!!)
            .addOnSuccessListener {
                if (it.signInMethods.isNullOrEmpty()) {
                    registerToNotesAppWithFireBase(googleSignInAccount)
                } else {
                    getIntoApp(googleSignInAccount.displayName.toString())
                }
            }
            .addOnFailureListener { displayToast("Failed !") }
    }

    private fun registerToNotesAppWithFireBase(googleSignInAccount: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnSuccessListener { }
            .addOnFailureListener { displayToast("Please try again later! Exception: ${it.message}") }
    }


    private fun getIntoApp(userName: String) {
        (requireActivity() as RegistrationActivity).goInApp(userName)
    }

    private fun displayToast(text: String) {
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
    }
}
