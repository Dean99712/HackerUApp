package com.example.hackeruapp.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.R
import com.example.hackeruapp.data.Repository
import com.example.hackeruapp.databinding.ActivityMainBinding
import com.example.hackeruapp.model.person.Person
import com.example.hackeruapp.ui.register.person.PersonAdapter
import com.example.hackeruapp.ui.register.LoginActivity
import com.example.hackeruapp.ui.register.person.PersonFragmentDirections
import com.example.hackeruapp.util.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var person: Person? = null
    private var adapter = PersonAdapter(
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
        setSupportActionBar(binding.bottomNavBar)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setButtonClickListener() {

        val button = findViewById<FloatingActionButton>(R.id.fab)
        val input = findViewById<EditText>(R.id.item_name_input)
        button.setOnClickListener {

//            val directions = PersonFragmentDirections.actionGlobalPersonFragment()
//            findNavController(R.id.fragmentContainerView).navigate(directions)
            addPersonToList(input.text.toString())
            if (input.text.toString().isNotEmpty())
                input.setText("")
            adapter.notifyDataSetChanged()
        }
        createRecyclerView()
    }

//    private fun hideBottomAppBar() {
//        binding.run {
//            bottomNavBar.performHide()
//
//            bottomNavBar.animate().setListener(object : AnimatorListenerAdapter() {
//                var isCanceled = false
//                override fun onAnimationEnd(animation: Animator) {
//                    super.onAnimationEnd(animation)
//                    if (isCanceled) return
//                    bottomNavBar.visibility = View.GONE
//                    fab.visibility = View.INVISIBLE
//                }
//
//                override fun onAnimationCancel(animation: Animator) {
//                    super.onAnimationCancel(animation)
//                    isCanceled = true
//                }
//            })
//        }
//    }

    private val getContentFromCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        ImageManager.onImageResultFromCamera(result,person!!, this)
    }

    private val getContentFromGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
                ImageManager.onImageResultFromGallery(result, person!!, this)
            }

    private fun onPersonImageClick(): (person: Person) -> Unit = {
        person = it

        val dialog = MaterialAlertDialogBuilder(this)
        dialog.setTitle("Choose an image")
        dialog.setMessage("Choose image for ${person!!.name}")

        dialog.setNeutralButton("Camera") { dialogInterface: DialogInterface, i: Int ->
            ImageManager.takePicture(person!!, getContentFromCamera)
        }
        dialog.setPositiveButton("Gallery") { dialogInterface: DialogInterface, i: Int ->

            ImageManager.getImageFromGallery(person!!, getContentFromGallery)
        }
        dialog.setNegativeButton("Network") { dialogInterface: DialogInterface, i: Int ->
            ImageManager.getImageFromApi(person!!, this)
        }
        dialog.show()
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
            NotificationsManager.displayNotification(this@MainActivity, Person(input))

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

            MainScope().launch(Dispatchers.IO) {
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

    fun fab() {

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







