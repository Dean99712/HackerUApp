package com.example.hackeruapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.model.Repository
import com.example.hackeruapp.ui.RecyclerAdapter
import com.example.hackeruapp.ui.SwipeToDeleteCallBack
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private var person: Person? = null
    private var adapter = RecyclerAdapter(
        arrayListOf(),
        onPersonTitleClick(),
        onPersonImageClick(),
        onPersonCardClick(),
        this
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
            addPersonToList(input.text.toString())
        }
        createRecyclerView()
    }


    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            ImageManager.onImageResultFromGallery(result, person!!, this)
        }


    private fun onPersonImageClick(): (person: Person) -> Unit = {
        person = it
        ImageManager.getImageFromGallery(it, getContent)
    }

    private fun addPersonToList(input: String) {
        if (!isInputValid(input)) {
            Toast.makeText(this, "Please enter a valid Input", Toast.LENGTH_SHORT).show()
        } else {
            thread(start = true) {
                Repository.getInstance(this).addPerson(Person(input))
            }
            Toast.makeText(this, "$input has Successfully added!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInputValid(input: String): Boolean {
        if (input.contains("[0-9]+?-?|/D[a-zA-Z]{3,}$[0-9]".toRegex()) || input.isNullOrEmpty()) {
            return false
        }
        return true
    }

    private fun onPersonTitleClick(): (Person) -> Unit = { person ->
        RecyclerFunctions.onPersonTitleClick(this, person)
    }

    private fun onPersonCardClick(): (Person) -> Unit = { person ->
        RecyclerFunctions.onPersonClick(this, person)
    }

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
                thread(start = true) {
                    Repository.getInstance(applicationContext).deletePerson(person)
                }
                Toast.makeText(this@MainActivity, "You removed ${person.name}!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    private fun isUserLogout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_om -> {
                isUserLogout()
                true
            }
            R.id.about_om -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}







