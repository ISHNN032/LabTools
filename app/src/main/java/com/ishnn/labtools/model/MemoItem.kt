package com.ishnn.labtools.model
import java.io.Serializable
import java.net.URL
import java.util.*

data class MemoItem(
    val title: String? = null,
    val content: String? = null,
    val time: Date? = null,
    val hasImage: Boolean = false
) : Serializable