package com.ishnn.labtools.ui.community.post

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.graphics.get
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.UserProfile
import com.ishnn.labtools.model.CommentItem
import com.ishnn.labtools.model.FavoriteItem
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
import kotlinx.android.synthetic.main.fragment_postcontent.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*


object PostManager {
    const val GET_GALLERY_IMAGE_POST = 100
    const val GET_GALLERY_IMAGE_COMMENT = 200

    fun addPost() {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }
        Global.db.collection("postContent").add(
            PostContent(
                "포스트내용입니다.",
                false
            )
        ).addOnSuccessListener {
            Global.db.collection("post").document(it.id).set(
                PostItem(
                    it.id,
                    "title",
                    Date(System.currentTimeMillis()),
                    GlobalLogin.getUserData()!!.id,
                    false,
                    0,
                    false
                )
            )
        }
    }

    fun addPost(
        title: String?,
        content: String?,
        hasImage: Boolean,
        Images: MutableMap<String, Uri>?,
        context: Context?
    ) {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }
        Global.db.collection("postContent").add(
            PostContent(
                content,
                hasImage = hasImage
            )
        ).addOnSuccessListener {
            Global.db.collection("post").document(it.id).set(
                PostItem(
                    it.id,
                    title,
                    Date(System.currentTimeMillis()),
                    GlobalLogin.getUserData()!!.id,
                    false,
                    0,
                    hasImage = hasImage
                )
            )

            if (hasImage && !Images.isNullOrEmpty()) {
                var first = true
                for (image in Images) {
                    if (first) {
                        first = false
                        var bitmap = MediaStore.Images.Media.getBitmap(
                            context?.contentResolver,
                            image.value
                        )
                        bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                        val bytes = stream.toByteArray()
                        Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${it.id}/${Global.CROPPED_IMAGE}")
                            .putBytes(bytes)
                    }
                    Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${it.id}/${image.key}")
                        .putFile(image.value)
                }
            }
        }
    }

    fun addPostComment(
        postId: String,
        commentIdToNest: String?,
        content: String?,
        hasImage: Boolean,
        isNested: Boolean,
        image: Uri?
    ) {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }

        var commentId = "000"
        if (!commentIdToNest.isNullOrEmpty()) {
            val callbackLast: (last: String?) -> Unit = { nestedLast ->
                if (commentIdToNest.split("n").size > 1) {
                    var nestedId = commentIdToNest.split("n")[1]
                    nestedId = "n" + (nestedId.toInt(10) + 1).toString().padStart(3, '0')
                    commentId = (commentIdToNest.padStart(3, '0')).plus(nestedId)
                }
                commentId = (commentIdToNest.padStart(3, '0')).plus("n000")
            }
            getNestedCommentLast(postId, commentIdToNest, callback = callbackLast)
        } else {
            val callbackLast: (last: String?) -> Unit = { last ->
                commentId = (last!!.toInt(10) + 1).toString().padStart(3, '0')
            }
            getCommentLast(postId, callbackLast)
        }


        Global.db.collection("postContent").document(postId).collection("comment").document(
            commentId
        ).set(
            CommentItem(
                commentId, content,
                Date(System.currentTimeMillis()),
                GlobalLogin.getUserData()!!.id,
                hasImage, isNested
            )
        )

        if (hasImage && image != null) {
            Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}/$commentId.jpg")
                .putFile(
                    image
                )
        }
    }

    fun deletePost(postId: String, hasImage: Boolean) {
        if (hasImage) {
            Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}").listAll()
                .addOnSuccessListener {
                    for (item in it.items) {
                        Global.storage.reference.child(item.path).delete()
                    }
                }
        }
        Global.db.collection("postContent").document(postId).delete()
            .addOnSuccessListener {
                Global.db.collection("post").document(postId).delete()
            }
    }

    fun deleteComment(postId: String, commentId: String, hasImage: Boolean) {
        if (hasImage) {
            Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}/${commentId}")
                .delete()
        }
        Global.db.collection("postContent").document(postId).collection("comment").document(
            commentId
        ).delete()
    }

    fun getPosts(callback: (List<PostItem>) -> Unit, keyword:String?) {
        if(keyword.isNullOrBlank()){
            Global.db.collection("post").orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    callback(result.toObjects(PostItem::class.java))
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }else{
            Global.db.collection("post").whereGreaterThanOrEqualTo("title", keyword)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    callback(result.toObjects(PostItem::class.java))
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }
    }

    fun getPostContent(postId: String, callback: (content: PostContent?) -> Unit) {
        Global.db.collection("postContent").document(postId).get().addOnSuccessListener { result ->
            callback(result.toObject(PostContent::class.java))
        }
    }

    fun getCommentCount(postId: String, callback: (content: Int?) -> Unit) {
        Global.db.collection("postContent").document(postId).collection("comment").get()
            .addOnSuccessListener { result ->
                callback(result.size())
            }
    }

    fun updateCommentCount(postId: String) {
        val callbackComment: (content: Int?) -> Unit = { count ->
            Global.db.collection("post").document(postId).update("commentCount", count)
        }
        getCommentCount(postId, callback = callbackComment)
    }

    fun getCommentLast(postId: String, callback: (content: String?) -> Unit) {
        var last = "000"
        Global.db.collection("postContent").document(postId).collection("comment").get()
            .addOnSuccessListener { result ->
                for (comment in result) {
                    if (last < comment.id) {
                        last = comment.id
                    }
                }
                callback(last)
            }
    }

    fun getNestedCommentLast(
        postId: String,
        commentId: String,
        callback: (content: String?) -> Unit
    ) {
        var last = postId
        Global.db.collection("postContent").document(postId).collection("comment").get()
            .addOnSuccessListener { result ->
                for (comment in result) {
                    if (comment.id.contains(commentId)) {
                        if (last < comment.id) {
                            last = comment.id
                        }
                    }
                }
                callback(last)
            }
    }

    fun getPostComments(postId: String, callback: (List<CommentItem>) -> Unit) {
        Global.db.collection("postContent").document(postId).collection("comment")
            .orderBy("commentId", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
                callback(result.toObjects(CommentItem::class.java))
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    fun getFavorites(callback: (PostItem) -> Unit, keyword: String?) {
        if (GlobalLogin.getUserData() == null) return

//        val ref = Global.db.collection("post").document("2nSmnvB49RqQ0rou2Vcx")
//        val ref2 = Global.db.collection("post").document("4gt7pyzqTkqnWqvtz2DQ")
//        val data = hashMapOf("favorites" to listOf<DocumentReference>(ref, ref2))
//        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString()).set(data)

//        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
//            .get()
//            .addOnSuccessListener { result ->
//                val list = result.get("favorites") as List<DocumentReference>
//                for (data in list) {
//                    data.get().addOnSuccessListener {
//                        if (it.exists()) {
//                            callback(it.toObject(PostItem::class.java)!!)
//                        } else {
//                            callback(PostItem())
//                        }
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("TAG", "Error getting documents: ", exception)
//            }
        if(keyword.isNullOrBlank()){
            Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
                .collection("favorite").orderBy("time", Query.Direction.DESCENDING).get().addOnSuccessListener { result ->
                    for (data in result.toObjects(FavoriteItem::class.java)) {
                        data.reference?.get()?.addOnSuccessListener {
                            if (it.exists()) {
                                callback(it.toObject(PostItem::class.java)!!)
                            } else {
                                callback(PostItem())
                            }
                        }
                    }
                }
        }else{
            Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
                .collection("favorite").whereGreaterThanOrEqualTo("title", "$keyword").get().addOnSuccessListener { result ->
                    for (data in result.toObjects(FavoriteItem::class.java)) {
                        data.reference?.get()?.addOnSuccessListener {
                            if (it.exists()) {
                                callback(it.toObject(PostItem::class.java)!!)
                            } else {
                                callback(PostItem())
                            }
                        }
                    }
                }
        }
    }

    fun addFavorite(postId: String) {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }
        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
            .collection("favorite").document(postId).set(
                FavoriteItem(postId, Global.db.collection("post").document(postId) ,Date(System.currentTimeMillis()))
            )
    }
    fun isFavorite(postId: String, callback: (Boolean) -> Unit){
        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
            .collection("favorite").document(postId).get().addOnSuccessListener {
                callback(it.exists())
            }
    }
    fun deleteFavorite(postId: String) {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }
        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
            .collection("favorite")
            .document(postId).delete()
    }

    fun getNotices(callback: (List<PostItem>) -> Unit, keyword: String?) {
        if(keyword.isNullOrBlank()){
            Global.db.collection("post").whereEqualTo("notice", true)
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    callback(result.toObjects(PostItem::class.java))
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }else{
            Global.db.collection("post").whereGreaterThanOrEqualTo("title", "$keyword").whereEqualTo("notice", true)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("TAG", "${document.id} => ${document.data}")
                    }
                    callback(result.toObjects(PostItem::class.java))
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }
    }

    fun getUserName(id: Long, callback: (name: String?) -> Unit) {
        Global.db.collection("user").document(id.toString()).get()
            .addOnSuccessListener { result ->
                val user = result.toObject(UserProfile::class.java)
                callback(user!!.nickName)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    fun addImage(postId: String, imageName: String) {

    }
}