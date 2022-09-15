package com.example.hackeruapp

import android.widget.TextView
import androidx.fragment.app.Fragment

class PersonFragment : Fragment(R.layout.person_fragment) {
    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        val titleTextView = activity.findViewById<TextView>(R.id.note_title)
        val descTextView = activity.findViewById<TextView>(R.id.note_desc)
        val title = requireArguments().getString("noteTitle")
        val desc = requireArguments().getString("noteDesc")
        titleTextView.text = title
        descTextView.text = "Description: $desc"
    }
}