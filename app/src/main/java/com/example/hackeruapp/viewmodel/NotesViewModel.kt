package com.example.hackeruapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hackeruapp.model.Note
import com.example.hackeruapp.model.Repository

class NotesViewModel(val app: Application) : AndroidViewModel(app) {
    private val repository = Repository.getInstance(app.applicationContext)

    fun addNote(note: Note) {
        repository.addNote(note)
    }

    val notesListLiveData: LiveData<List<Note>> = repository.getAllNotesAsLiveData()

    val currentTitleLiveData: MutableLiveData<String> = MutableLiveData()
}