package com.example.hackeruapp

import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class PersonFragment : Fragment(R.layout.person_fragment) {



        val personTextView =  activity?.findViewById<TextView>(R.id.fragment_person_details)
        val personImage =  activity?.findViewById<ImageView>(R.id.fragment_person_image)
        override fun onResume() {
            super.onResume()
            val personTextView = activity?.findViewById<TextView>(R.id.fragment_person_details)
            val personImage = activity?.findViewById<ImageView>(R.id.fragment_person_image)

            val name = requireArguments().getString("name")
            val image = requireArguments().getInt("image")

            personTextView?.text = name.toString()
            personImage?.setImageResource(image)
        }
    }