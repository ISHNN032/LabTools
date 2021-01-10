package com.ishnn.labtools.model
import java.io.Serializable
import java.net.URL
import java.util.*

data class CommentItem(
    val commentId: String? = null,
    val content: String? = null,
    val time: Date? = null,
    val user: Long? = null,
    val hasImage: Boolean = false,
    val nested: Boolean = false
) : Serializable