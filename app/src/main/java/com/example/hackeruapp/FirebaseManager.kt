package com.example.hackeruapp

import android.content.Context
import com.example.hackeruapp.model.Note
import com.example.hackeruapp.model.SharedPreferencesManager
import com.example.hackeruapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseManager private constructor(val context: Context) {

    val db = Firebase.firestore
    val usersCollection = "Users"

    companion object{
        private lateinit var instance: FirebaseManager

        fun getInstance(context: Context): FirebaseManager {
            if (!Companion::instance.isInitialized)
                instance = FirebaseManager(context)
            return instance
        }
    }

    fun addUser(user: User): Task<Void> {
       return db.collection(usersCollection).document(user.email).set(user)
    }

    fun getUser(): Task<DocumentSnapshot> {
        return db.collection(usersCollection).document("dean2910997@gmail.com").get()
    }

    fun addNoteToUser(note: Note) {
        val user = SharedPreferencesManager.myUser
        user.notes.notes.add(note)
        db.collection(usersCollection).document(SharedPreferencesManager.myUser.email).set(user)
    }
}