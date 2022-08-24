package com.francisco.strider.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val message: String,
    val creation: Date
): Parcelable {

    companion object {
        fun mock() = Post(message = "my first post", creation = Date())
    }
}