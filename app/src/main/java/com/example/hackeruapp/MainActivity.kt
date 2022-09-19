package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var adapter = RecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonClickListener()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.add_button)
        val input = findViewById<EditText>(R.id.item_name_input)
        button.setOnClickListener {
            adapter.notifyDataSetChanged()

            if (!isInputValid()) {

                Toast.makeText(this, "Please enter a valid Input", Toast.LENGTH_SHORT).show()
            } else {

                thread(start = true) {
                    Repository.getInstance(this).addPerson(Person(input.text.toString(), R.drawable.ic_person))
                }
                Toast.makeText(this, "Successfully Added!", Toast.LENGTH_SHORT).show()
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

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        val peopleListLiveData =  Repository.getInstance(this).getAllPeopleAsLiveData()
        peopleListLiveData.observe(this) { personList ->
            adapter.viewUpdater(personList)
        }
    }

}






