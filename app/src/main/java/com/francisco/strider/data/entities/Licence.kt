package com.francisco.strider.data.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
class Licence(
    val key: String? = null,
    val name: String? = null,
    @Json(name = "spdx_id")
    val spdxId: String? = null,
    val url: String? = null,
    @Json(name = "node_id")
    val nodeId: String? = null,
): Parcelable
