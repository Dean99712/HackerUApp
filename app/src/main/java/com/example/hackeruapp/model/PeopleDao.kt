package com.example.hackeruapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PeopleDao {

    @Insert
    fun insertPerson(person: Person)

    @Query("UPDATE peopleTable SET person_name =:person WHERE id = :id")
    fun updatePerson(id: Int, person: String)

    @Delete
    fun deletePerson(person: Person)

    @Query("Select * from peopleTable")
    fun getAllPeople(): LiveData<List<Person>>

}
