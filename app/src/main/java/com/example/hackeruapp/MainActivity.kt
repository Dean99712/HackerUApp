package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private var personList = arrayListOf<Person>()
    var adapter = RecyclerAdapter(personList) {
        displayPersonFragment(it)
//        Toast.makeText(this,"Hello ${it.name}",Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButtonClickListener()
    }

    private fun displayPersonFragment(person: Person) {
        val bundle = bundleOf("name" to person.name, "image" to person.image)
        val personFragment = PersonFragment()
        personFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .add(R.id.person_details_fragment, personFragment)
            .commit()
    }

    fun removePersonFragment(view: View) {
        val fragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.person_details_fragment)

        if (fragment != null)
            supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.add_button)
        val input = findViewById<EditText>(R.id.item_name_input)

        button.setOnClickListener {
            val radioGroup = findViewById<RadioGroup>(R.id.radioItemSelect)
            val checkedId = radioGroup.checkedRadioButtonId
            adapter.notifyDataSetChanged()

            if (input.text.isDigitsOnly() || input.text.isNullOrEmpty())
                Toast.makeText(this, "Please enter a valid Input", Toast.LENGTH_SHORT).show()
            else {
                if (checkedId == -1)
                    Toast.makeText(this, "Please select an image!", Toast.LENGTH_SHORT).show()

                when (checkedId) {
                    R.id.radioButton1 -> personList.add(
                        Person(
                            input.text.toString(),
                            R.drawable.avatar1_foreground
                        )
                    )
                    R.id.radioButton2 -> personList.add(
                        Person(
                            input.text.toString(),
                            R.drawable.avatar2_foreground
                        )
                    )
                    R.id.radioButton3 -> personList.add(
                        Person(
                            input.text.toString(),
                            R.drawable.avatar3_foreground
                        )
                    )
                }
            }
        }
        createRecyclerView()
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
    }

}






