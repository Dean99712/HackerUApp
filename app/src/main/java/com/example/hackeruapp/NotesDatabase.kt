package com.example.hackeruapp

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {

        fun getDatabase(context: Context): NotesDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NotesDatabase::class.java,
                "notes_database"
            ).build()
        }
    }
}