package com.example.hackeruapp

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.model.Repository
import kotlin.concurrent.thread

object RecyclerFunctions {

    fun onPersonClick(context: Context, person: Person) {

        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.person_fragment, null)

        val dialogProfileImage: ImageView = dialogView.findViewById(R.id.fragment_person_image)
        val dialogProfileName: TextView = dialogView.findViewById(R.id.fragment_person_name)
        val dialogProfileDetails: TextView = dialogView.findViewById(R.id.fragment_person_details)
        dialogProfileName.text = person.name
        dialogProfileDetails.text = person.details
        if (person.imagePath == null)
            dialogProfileImage.setImageResource(R.drawable.ic_person)
        else
            dialogProfileImage.setImageURI(Uri.parse(person.imagePath))
        dialog.setView(dialogView)
        dialog.setCancelable(true)
        dialog.show()
    }

    fun onPersonTitleClick(context: Context, person: Person) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val dialogView: View = LayoutInflater.from(context).inflate(R.layout.fragment_update, null)

        dialog.setView(dialogView)
        dialog.setPositiveButton("Update") { dialog, which ->
            val dialogPersonName =
                dialogView.findViewById<EditText?>(R.id.et_person_update_name).text.toString()
            val dialogPersonDetails =
                dialogView.findViewById<EditText?>(R.id.et_person_update_details).text.toString()

            if (dialogPersonName.isNullOrEmpty() || dialogPersonDetails.isNullOrEmpty()) {
                Toast.makeText(
                    context,
                    "Failed to update ${person.name}! Please try again...",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                thread(start = true) {
                    Repository.getInstance(context)
                        .updatePerson(person.id, dialogPersonName, dialogPersonDetails)
                }
                Toast.makeText(context, "${person.name} updated successfully!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.setNeutralButton("Cancel") { dialog, which -> }
        dialog.setCancelable(true)
        dialog.show()
    }
}