package com.example.hackeruapp

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        }

    private fun displayPersonDetailsFragment() {
        val personFragment = PersonFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment, personFragment)
            .commit()
    }

    private fun setButton3ClickListener() {
        val button = findViewById<Button>(R.id.hello_button)
        button.setOnClickListener {
            Toast.makeText(this, "Hi im toast", Toast.LENGTH_SHORT).show()
            createRecyclerView()

            val secondTitleTextView = TextView(applicationContext)

        }

    }

    private fun createViewProgremathic() {

        val secondTitle
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
    }

    private fun createRecyclerView() {


    }

}
