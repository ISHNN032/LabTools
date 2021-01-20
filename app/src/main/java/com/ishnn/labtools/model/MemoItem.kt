package com.ishnn.labtools.model
import java.io.Serializable
import java.net.URL
import java.util.*

data class MemoItem(
    val postId: String? = null,
    val title: String? = "메모",
    val hasImage: Boolean = false
) : Serializable