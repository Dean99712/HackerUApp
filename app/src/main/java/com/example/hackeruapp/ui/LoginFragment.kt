package com.example.hackeruapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.hackeruapp.R
import com.example.hackeruapp.viewmodel.RegistrationViewModel

class LoginFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        val editText = requireActivity().findViewById<EditText>(R.id.email_login_tv)
        editText.setText(registrationViewModel.currentEmail)
        editText.addTextChangedListener  {
            registrationViewModel.currentEmail = it.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}