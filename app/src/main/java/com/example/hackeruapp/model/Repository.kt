package com.example.hackeruapp.model

import android.content.Context
import androidx.lifecycle.LiveData

class Repository private constructor(context: Context) {
    private val dao = PersonDatabase.getDatabase(context).getNotesDao()

    companion object{
        private lateinit var instance: Repository

        fun getInstance(context: Context) : Repository {
            if (!Companion::instance.isInitialized){
                instance = Repository(context)
            }
            return instance
        }
    }

    fun getAllPeopleAsLiveData(): LiveData<List<Person>> {
        return dao.getAllPeople()
    }

    fun addPerson(person: Person) {
        dao.insertPerson(person)
    }

    fun deletePerson(person: Person) {
        dao.deletePerson(person)
    }

    fun updatePerson(id : Int , person: String){
        return dao.updatePerson(id , person)
    }
}