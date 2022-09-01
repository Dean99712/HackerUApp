package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton3ClickListener()

        }

    private fun setButton3ClickListener() {
        val button = findViewById<Button>(R.id.add_button)
        button.setOnClickListener {
            createList()
//            createRecyclerView()

        }

    }

    private fun getPersonList(): MutableList<Person> {

        val personList = mutableListOf<Person>()

        personList.add(Person("Daniella", 30))
        personList.add(Person("Boaz", 52))
        personList.add(Person("Shoam", 26))
        personList.add(Person("Ron", 21))
        personList.add(Person("Naor", 43))
        personList.add(Person("Mia", 29))
        personList.add(Person("Oriel", 23))

        return personList
    }

    private fun createList() {
        val listView = findViewById<ListView>(R.id.list_view)
        val personList = getPersonList()
        val myAdapter = ArrayAdapter(this, R.layout.item_layout, personList)
        listView.adapter = myAdapter
    }

//    private fun createRecyclerView() {
//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        val personList = getPersonList()
//        val adapter = RecyclerAdapter(personList)
//        recyclerView.adapter = adapter
//
//
//    }

}
