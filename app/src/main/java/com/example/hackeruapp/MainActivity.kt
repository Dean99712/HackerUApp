package com.example.hackeruapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.model.Repository
import com.example.hackeruapp.ui.RecyclerAdapter
import com.example.hackeruapp.ui.SwipeToDeleteCallBack
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var adapter = RecyclerAdapter(
        arrayListOf(),
        onPersonTitleClick(),
        onRemoveButtonClick()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        val button = findViewById<FloatingActionButton>(R.id.add_button)
        val input = findViewById<EditText>(R.id.item_name_input)
        button.setOnClickListener {
            adapter.notifyDataSetChanged()

            if (!isInputValid()) {
                Toast.makeText(this, "Please enter a valid Input", Toast.LENGTH_SHORT).show()
            } else {

                thread(start = true) {
                    Repository.getInstance(this)
                        .addPerson(Person(input.text.toString(), R.drawable.ic_person))
                }
                Toast.makeText(this, "${input.text} has Successfully added!", Toast.LENGTH_SHORT).show()
            }
        }
        createRecyclerView()
    }

    private fun isInputValid(): Boolean {
        val input = findViewById<EditText>(R.id.item_name_input)
        if (input.text.contains("[0-9]+?-?|/D[a-zA-Z]{3,}$[0-9]".toRegex()) || input.text.isNullOrEmpty()) {
            return false
        }
        return true
    }


    private fun onPersonTitleClick(): (Person) -> Unit = { person ->
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.fragment_update, null)

        dialog.setView(dialogView)
        dialog.setPositiveButton("Update") { dialog, which ->
            val dialogPersonName =
                dialogView.findViewById<EditText?>(R.id.et_person_update_name).text.toString()

            thread(start = true) {
                Repository.getInstance(this).updatePerson(person.id, dialogPersonName)
            }
            Toast.makeText(this,"${person.name} updated successfully!", Toast.LENGTH_SHORT).show()
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun onRemoveButtonClick(): (person: Person) -> Unit = { person ->
        Toast.makeText(this, "Success ${person.name}", Toast.LENGTH_SHORT).show()

        showAlertDialogRemoveBtn(person)
    }

    fun showAlertDialogRemoveBtn(person: Person) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete?")
        builder.setPositiveButton("Confirm") { dialog, which ->
            thread(start = true) {
                Repository.getInstance(this).deletePerson(person)
            }
            adapter.notifyItemRemoved(person.id)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
        }
        builder.show()
    }
//        private fun onPersonImageClick(): (Person) -> Unit {
//
//        }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        val peopleListLiveData = Repository.getInstance(this).getAllPeopleAsLiveData()
        peopleListLiveData.observe(this) { personList ->
            adapter.viewUpdater(personList)
        }

        val swipeToDeleteCallBack = object : SwipeToDeleteCallBack() {
            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val person = adapter.dataList[position]
                thread(start = true){
                    Repository.getInstance(applicationContext).deletePerson(person)
                }
                Toast.makeText(this@MainActivity, "You removed ${person.name}!",Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
}







