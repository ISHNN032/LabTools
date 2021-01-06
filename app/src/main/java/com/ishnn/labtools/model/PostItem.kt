package com.ishnn.labtools.model
import java.io.Serializable
import java.net.URL
import java.util.*

data class PostItem(
    val postId: String? = null,
    val title: String? = "없는 게시물입니다.",
    val time: Date? = null,
    val user: Long? = null,
    val notice: Boolean? = false,
    val commentCount: Int? = 0,
    val favoriteCount: Int? = 0,
    val imageUrl: URL? = null
) : Serializable