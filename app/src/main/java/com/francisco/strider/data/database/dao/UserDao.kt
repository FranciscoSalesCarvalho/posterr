package com.francisco.strider.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francisco.strider.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUser(id: Long): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)
}