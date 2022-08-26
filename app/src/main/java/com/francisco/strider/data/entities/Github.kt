package com.francisco.strider.data.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Github(
    @Json(name = "total_count")
    val totalCount: Long? = null,
    @Json(name = "incomplete_results")
    val incompleteResult: Boolean? = null,
    val items: List<Item>? = null
) : Parcelable