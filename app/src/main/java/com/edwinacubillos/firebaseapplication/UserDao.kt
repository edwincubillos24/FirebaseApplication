package com.edwinacubillos.firebaseapplication

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user_entity")
    fun getAllUsers(): MutableList<User>

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)
}

