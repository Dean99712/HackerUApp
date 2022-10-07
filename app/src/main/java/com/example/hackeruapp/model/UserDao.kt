package com.example.hackeruapp.model

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)


}