package com.example.hackeruapp.ui.register.person



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.hackeruapp.databinding.FragmentPersonListBinding



class PersonFragment() : Fragment() {

    private lateinit var binding: FragmentPersonListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPersonListBinding.inflate(layoutInflater)
        return binding.root
    }

    private val nonInboxOnBackCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {

        }
    }


}