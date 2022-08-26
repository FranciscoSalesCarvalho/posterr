package com.francisco.strider.data.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Event(
    val people: @RawValue List<Any>? = null,
    val date: Long? = null,
    val description: String? = null,
    val image: String? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val price: Double? = null,
    val title: String? = null,
    val id: String? = null
) : Parcelable
