package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private lateinit var repository: Repository
    private val noteList = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val name = intent.extras!!.get("name")
        findViewById<TextView>(R.id.title_text_view).text = "Hi ${name}"
    }

    override fun onStart() {
        super.onStart()
        setButtonClickListener()
        val dao = NotesDatabase.getDatabase(application).getNotesDao()
        repository = Repository(dao)

        createRecyclerView()
    }

    private fun displayPersonDetailsFragment(note: Note) {
        val personFragment = PersonFragment()
        val bundle = bundleOf("thePersonAge" to note.title, "thePersonName" to note.description)
        personFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, personFragment)
            .commit()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val noteTitle = findViewById<EditText>(R.id.note_title).text.toString()
            val noteDesc = findViewById<EditText>(R.id.note_description).text.toString()
            val note = Note(noteTitle, noteDesc)
            thread(start = true) {
                repository.addNote(note)
            }
        }
    }


    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(noteList) {
            displayPersonDetailsFragment(it)
        }
        recyclerView.adapter = adapter
        repository.getAllNotes().observe(this) {
            adapter.updateAdapterView(it)
        }
    }
}
