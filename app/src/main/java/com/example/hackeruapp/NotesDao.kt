package com.example.hackeruapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert
    fun insetNote(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("Select * from notesTable")
    fun getAllNotes(): LiveData<List<Note>>

    @Update
    fun updateNote(note: Note)

    fun updateNoteImageUri(note: Note, uri: String, imageType: IMAGE_TYPE) {
        note.imagePath = uri
        note.imageType = imageType
        updateNote(note)
    }
}