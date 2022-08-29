package com.example.hackeruapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class PersonFragment : Fragment(R.layout.person_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personTextView = activity?.findViewById<TextView>(R.id.person_title)
        personTextView?.text = " Ron"
    }
}