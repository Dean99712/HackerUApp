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
import retrofit2.Retrofit
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository
    private var chosenNote: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val name = intent.extras!!.get("name")
//        findViewById<TextView>(R.id.title_text_view).text = "Hey ${name}"
    }

    override fun onStart() {
        super.onStart()
        repository = Repository(application)
        setButtonClickListener()
        createRecyclerView()
    }

    private fun displayPersonDetailsFragment(note: Note) {
        val personFragment = PersonFragment()
        val bundle = bundleOf("noteTitle" to note.title, "noteDesc" to note.description)
        personFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, personFragment)
            .commit()
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val noteTitle = findViewById<EditText>(R.id.note_title_et).text.toString()
            val noteDesc = findViewById<EditText>(R.id.note_desc_et).text.toString()
            val note = Note(noteTitle, noteDesc)
            thread(start = true) {
                repository.addNote(note)
            }
        }
    }

    private fun onNoteTitleClick(): (note: Note) -> Unit = {
        displayPersonDetailsFragment(it)
    }

    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("Test", "Click on : $result ")
            if (result.resultCode == RESULT_OK) {
                var uri = result.data!!.data
                if (uri != null) {
                    contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    thread(start = true) {
                        repository.updateNoteImage(chosenNote!!, uri)
                    }
                }
            }
        }

    private fun onNoteImageClick(): (note: Note) -> Unit = { note ->
        println("Clicked on ${note.title}")
        chosenNote = note
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf(), onNoteTitleClick(), onNoteImageClick())

        recyclerView.adapter = adapter
        val notesListLiveData = repository.getAllNotesAsLiveData()
        notesListLiveData.observe(this) { noteList ->
            adapter.heyAdapterPleaseUpdateTheView(noteList)
        }
    }

    fun makeGetRequestCall(){
        val retroFit =  ApiInterface.create()
    }
}
