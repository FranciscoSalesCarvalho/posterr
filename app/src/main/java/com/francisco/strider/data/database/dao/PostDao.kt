package com.francisco.strider.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francisco.strider.data.entities.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)
}