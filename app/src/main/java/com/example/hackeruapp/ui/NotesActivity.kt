package com.example.hackeruapp.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.*
import com.example.hackeruapp.model.IMAGE_TYPE
import com.example.hackeruapp.model.Note
import com.example.hackeruapp.model.Repository
import com.example.hackeruapp.viewmodel.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class NotesActivity : AppCompatActivity() {

    private val notesViewModel: NotesViewModel by viewModels()


    private var chosenNote: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val serviceIntent = Intent(this, NotesService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
        updateTitleDynamically()
    }

    override fun onStart() {
        super.onStart()
        setButtonClickListener()
        createRecyclerView()
        val userName =
            getSharedPreferences(R.string.app_name.toString(), MODE_PRIVATE).getString(
                "USER_NAME",
                ""
            )
        title_text_view.text = "Hello " + userName
    }

    private fun displayPersonDetailsFragment(note: Note) {
        val noteItemFragment = NoteItemFragment()
        val bundle = bundleOf("thePersonAge" to note.description, "thePersonName" to note.title)
        noteItemFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, noteItemFragment)
            .commit()
    }

    private fun createNewNote(): Note {
        val noteTitle = findViewById<EditText>(R.id.note_title_et).text.toString()
        val noteDesc = findViewById<EditText>(R.id.note_desc_et).text.toString()
        val note = Note(noteTitle, noteDesc)
        notesViewModel.viewModelScope.launch(Dispatchers.IO) {
            notesViewModel.addNote(note)
        }
        return note
    }

    private fun setButtonClickListener() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            createNewNote()
        }
    }

    private fun onNoteTitleClick(): (note: Note) -> Unit = {
        displayPersonDetailsFragment(it)
    }

    val getContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            ImagesManager.onImageResultFromGallery(result, chosenNote!!, this)
        }

    private fun addImageToNote(imagePath: String, imageType: IMAGE_TYPE) {
        thread(start = true) {
            Repository.getInstance(this).updateNoteImage(chosenNote!!, imagePath, imageType)
        }
    }

    private fun getImageFromGallery(note: Note) {
        Log.d("Test", "Click on ${note.title}")
        chosenNote = note
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun onNoteImageClick(): (note: Note) -> Unit = { note ->
        chosenNote = note
        displayImagesAlertDialog(this, chosenNote!!,getContent )
    }

    private fun displayImagesAlertDialog(
        context: Context,
        note: Note,
        getContent: ActivityResultLauncher<Intent>
    ) {
        notesViewModel.viewModelScope.launch(Dispatchers.Main) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Choose an image")
            alertDialogBuilder.setMessage("Choose image for ${note.title}")
            alertDialogBuilder.setNeutralButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }

            alertDialogBuilder.setPositiveButton("Gallery") { dialogInterface: DialogInterface, i: Int ->
                ImagesManager.getImageFromGallery(note, getContent)
            }
            alertDialogBuilder.setNegativeButton("Network") { dialogInterface: DialogInterface, i: Int ->
                notesViewModel.viewModelScope.launch(Dispatchers.IO) {
                    ImagesManager.getImageFromApi(note, context)
                }
            }
            alertDialogBuilder.show()
        }
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = MyAdapter(arrayListOf(), onNoteTitleClick(), onNoteImageClick(), this)
        recyclerView.adapter = adapter
        notesViewModel.notesListLiveData.observe(this) { notesList ->
            adapter.heyAdapterPleaseUpdateTheView(notesList)
        }
    }

    private fun updateTitleDynamically() {
        note_title_et.addTextChangedListener {
            notesViewModel.currentTitleLiveData.value = note_title_et.text.toString()
        }
        notesViewModel.currentTitleLiveData.observe(this) {
            title_text_view.text = "You are going to add: $it"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
