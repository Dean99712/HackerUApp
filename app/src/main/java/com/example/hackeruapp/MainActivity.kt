package com.example.hackeruapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.databinding.ActivityMainBinding
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.model.Repository
import com.example.hackeruapp.ui.RecyclerAdapter
import com.example.hackeruapp.ui.SwipeToDeleteCallBack
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragment = PersonFragment()
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonClickListener()
        val bottomNb = findViewById<BottomAppBar>(R.id.bottom_nav_bar)
        setSupportActionBar(bottomNb)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setButtonClickListener() {

        val button = findViewById<FloatingActionButton>(R.id.fab)
        val input = findViewById<EditText>(R.id.item_name_input)
        button.setOnClickListener {

            val personFragment =
                supportFragmentManager.beginTransaction().add(main_activity_layout.id, fragment)


            addPersonToList(input.text.toString())
            if (input.text.toString().isNotEmpty())
                input.setText("")
            adapter.notifyDataSetChanged()
        }
        createRecyclerView()
    }

    private fun hideBottomAppBar() {
        binding.run {
            bottomNavBar.performHide()

            bottomNavBar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (isCanceled) return
                    bottomNavBar.visibility = View.GONE
                    fab.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {
                    super.onAnimationCancel(animation)
                    isCanceled = true
                }
            })
        }
    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            ImageManager.onImageResultFromGallery(result, person!!, this)
        }

    private fun onPersonImageClick(): (person: Person) -> Unit = {
        person = it
        ImageManager.displayImagesAlertDialog(this, person!!, getContent)
    }

    private fun addPersonToList(input: String) {
        if (!isInputValid(input)) {
            Toast.makeText(this, "Please enter a valid Input", Toast.LENGTH_SHORT).show()
        } else {
            thread(start = true) {
                Repository.getInstance(this).addPerson(Person(input))
            }
            Snackbar.make(
                main_activity_layout,
                "$input has Successfully added!",
                Snackbar.LENGTH_SHORT
            ).show()
            NotificationViewer.displayNotification(this, Person(input))
        }
    }

    private fun isInputValid(input: String): Boolean {
        if (input.contains("^[a-zA-Z]*$".toRegex()) && input.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun onPersonTitleClick(): (Person) -> Unit = { person ->
        RecyclerFunctions.onPersonTitleClick(main_activity_layout, this, person)
    }

    private fun onPersonCardClick(): (Person) -> Unit = { person ->
        RecyclerFunctions.onPersonClick(this, person)
    }

    @OptIn(DelicateCoroutinesApi::class)
    val swipeToDeleteCallBack = object : SwipeToDeleteCallBack() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            val position = viewHolder.adapterPosition
            val personList = adapter.dataList
            val person = adapter.dataList[position]

            personList.removeAt(position)
            adapter.notifyItemRemoved(position)

            GlobalScope.launch(Dispatchers.IO) {
                Repository.getInstance(applicationContext).deletePerson(person)
            }


            Snackbar.make(
                main_activity_layout,
                "You removed ${person.name}!",
                Snackbar.LENGTH_LONG
            )
                .setAction("Undo") {
                    personList.add(position, person)
                    adapter.notifyItemInserted(position)
                    GlobalScope.launch(Dispatchers.IO) {
                        Repository.getInstance(applicationContext).addPerson(person)
                    }
                }.show()
        }
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
        val peopleListLiveData = Repository.getInstance(this).getAllPeopleAsLiveData()
        peopleListLiveData.observe(this) { personList ->
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
            itemTouchHelper.attachToRecyclerView(recyclerView)
            adapter.updateRecyclerView(personList)
        }
    }


    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_manu_nav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_om -> {
                logoutUser()
                true
            }
            R.id.home_om -> {
                true
            }
            R.id.search_om -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}







