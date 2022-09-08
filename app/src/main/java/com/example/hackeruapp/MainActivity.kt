package com.example.hackeruapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
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

    private fun onNoteTitleClick(): (note: Note) -> Unit {
        displayPersonDetailsFragment(it)
    }

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        Log.d("Test", "got content: ${result}")
        val url = result.data?.data
    }

    private fun onNoteImageClick(): (note: Note) -> Unit = { note ->
        println("Click on ${note.title}")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf(), onNoteTitleClick(), onNoteImageClick())
        recyclerView.adapter = adapter
        val notesListLiveData = repository.getAllNotes()
        notesListLiveData.observe(this) { noteList ->
            adapter.heyAdapterPleaseUpdateTheView(noteList)
        }
    }
}
