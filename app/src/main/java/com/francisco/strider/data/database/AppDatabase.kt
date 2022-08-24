package com.francisco.strider.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.francisco.strider.data.database.converter.DateConverter
import com.francisco.strider.data.database.dao.PostDao
import com.francisco.strider.data.database.dao.UserDao
import com.francisco.strider.data.entities.Post
import com.francisco.strider.data.entities.User

@Database(entities = [Post::class, User::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val postDao: PostDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.inMemoryDatabaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}