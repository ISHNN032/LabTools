package com.ishnn.labtools.model
import com.google.firebase.firestore.DocumentReference
import java.io.Serializable
import java.lang.ref.PhantomReference
import java.net.URL
import java.util.*

data class FavoriteItem(
    val postId: String? = null,
    val reference: DocumentReference? = null,
    val time: Date? = null
) : Serializable