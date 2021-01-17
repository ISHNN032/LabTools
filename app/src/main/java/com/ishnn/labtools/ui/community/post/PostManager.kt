package com.ishnn.labtools.ui.community.post

import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.UserProfile
import com.ishnn.labtools.model.CommentItem
import com.ishnn.labtools.model.PostContent
import com.ishnn.labtools.model.PostItem
import java.util.*
import kotlin.collections.ArrayList

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
                    0,
                    false
                )
            )
        }
    }

    fun addPost(title: String?, content: String?, hasImage: Boolean, Images: ArrayList<Uri>?) {
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
                    0,
                    hasImage = hasImage
                )
            )

            if(hasImage && !Images.isNullOrEmpty()){
                for(image in Images.withIndex()){
                    if(image.index == 0){
                        //Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${it.id}").putFile(image.value)
                        //Todo Preview Image
                    }
                    Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${it.id}").putFile(image.value)
                }
            }
        }
    }

    fun addPostComment(postId: String, commentId: String, content: String?, hasImage: Boolean, isNested: Boolean) {
        if (!GlobalLogin.getUserLoggedIn()) {
            return
        }

        Global.db.collection("postContent").document(postId).collection("comment").document(commentId).set(
            CommentItem(
                commentId, content,
                Date(System.currentTimeMillis()),
                GlobalLogin.getUserData()!!.id,
                hasImage, isNested
            )
        )
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
            Global.storage.reference.child("${Global.STORAGE_POST_CONTENT}${postId}/${commentId}").delete()
        }
        Global.db.collection("postContent").document(postId).collection("comment").document(commentId).delete()
    }

    fun getPosts(callback: (List<PostItem>) -> Unit) {
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
    }

    fun getPostContent(postId: String, callback: (content: PostContent?) -> Unit) {
        Global.db.collection("postContent").document(postId).get().addOnSuccessListener { result ->
            callback(result.toObject(PostContent::class.java))
        }
    }

    fun getPostComments(postId: String, callback: (List<CommentItem>) -> Unit) {
        Global.db.collection("postContent").document(postId).collection("comment").
        orderBy("commentId", Query.Direction.ASCENDING)
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

    fun getFavorites(callback: (PostItem) -> Unit) {
        if (GlobalLogin.getUserData() == null) return

//        val ref = Global.db.collection("post").document("2nSmnvB49RqQ0rou2Vcx")
//        val ref2 = Global.db.collection("post").document("4gt7pyzqTkqnWqvtz2DQ")
//        val data = hashMapOf("favorites" to listOf<DocumentReference>(ref, ref2))
//        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString()).set(data)

        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString())
            .get()
            .addOnSuccessListener { result ->
                val list = result.get("favorites") as List<DocumentReference>
                for (data in list) {
                    data.get().addOnSuccessListener {
                        if (it.exists()) {
                            callback(it.toObject(PostItem::class.java)!!)
                        } else {
                            callback(PostItem())
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    fun getNotices(callback: (List<PostItem>) -> Unit) {
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