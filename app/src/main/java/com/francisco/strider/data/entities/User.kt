package com.francisco.strider.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val username: String,
    val dateJoined: Date
): Parcelable {

    companion object {
        fun mock() = User(
            username = "Francisco Sales",
            dateJoined = Date()
        )
    }
}