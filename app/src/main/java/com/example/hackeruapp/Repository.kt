package com.example.hackeruapp

import androidx.lifecycle.LiveData
import androidx.room.Delete

class Repository(private val notesDao: NotesDao) {

    fun getAllNotes(): LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

    fun addNote(note: Note) {
        notesDao.insertNote(note)
    }
}