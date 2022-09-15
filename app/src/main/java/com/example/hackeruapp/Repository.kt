package com.example.hackeruapp

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData

class Repository(application: Application) {
    private val dao = NotesDatabase.getDatabase(application).getNotesDao()

    fun getAllNotesAsLiveData(): LiveData<List<Note>> {
        return dao.getAllNotes()
    }

    fun addNote(note: Note) {
        dao.insetNote(note)
    }

    fun deleteNote(note: Note) {
        dao.delete(note)
    }

    fun updateNoteImage(note: Note, uri: Uri) {
        dao.updateNoteImageUri(note, uri)
    }
}