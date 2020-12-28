package com.ishnn.labtools.model
import java.net.URL
import java.util.*

data class PostItem(
    val title: String? = null,
    val time: Date? = null,
    val user: Long? = null,
    val notice: Boolean? = null,
    val commentCount: Int? = null,
    val favoriteCount: Int? = null,
    val imageUrl: URL? = null
)