package com.example.hackeruapp.model

import android.content.Context
import androidx.lifecycle.LiveData

class Repository private constructor(applicationContext: Context) {
    private val dao = NotesDatabase.getDatabase(applicationContext).getNotesDao()

    companion object{
        private lateinit var instance: Repository

        fun getInstance(context: Context): Repository {
            if (!Companion::instance.isInitialized)
                instance = Repository(context)
            return instance
        }
    }
    fun getAllNotesAsLiveData(): LiveData<List<Note>> {
        return dao.getAllNotes()
    }

    fun addNote(note: Note) {
        dao.insertNote(note)
    }

    fun updateNoteImage(note: Note, uri: String, imageType: IMAGE_TYPE) {
        dao.updateNoteImageUri(note, uri, imageType)
    }
}