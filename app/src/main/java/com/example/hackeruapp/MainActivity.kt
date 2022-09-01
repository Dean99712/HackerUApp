package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var personList: ArrayList<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton3ClickListener()
    }

    private fun setButton3ClickListener() {
        val button = findViewById<Button>(R.id.add_button)
        button.setOnClickListener {
            createRecyclerView()

        }

    }

    private fun getPersonList(): ArrayList<Person> {

        val personList = arrayListOf<Person>()

        personList.add(Person("Daniella", 30))
        personList.add(Person("Boaz", 52))
        personList.add(Person("Shoam", 26))
        personList.add(Person("Ron", 21))
        personList.add(Person("Naor", 43))
        personList.add(Person("Mia", 29))
        personList.add(Person("Oriel", 23))

        return personList
    }


    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val personList = getPersonList()
        val adapter = RecyclerAdapter(personList)
        recyclerView.adapter = adapter


    }

}
