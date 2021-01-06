package com.ishnn.labtools.model
import java.io.Serializable
import java.net.URL
import java.util.*

data class CommentItem(
    val postId: String? = null,
    val replyOfId: String? = null,
    val content: String? = null,
    val time: Date? = null,
    val user: Long? = null
) : Serializable