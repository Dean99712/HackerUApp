package com.example.hackeruapp.model

import android.content.Context
import androidx.room.*

@Database(entities = arrayOf(Note::class, User::class), version = 1)

@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
    abstract fun getUsersDao(): UserDao

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