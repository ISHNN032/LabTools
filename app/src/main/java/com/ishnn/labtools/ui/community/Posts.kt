package com.ishnn.labtools.ui.community

import android.util.Log
import android.webkit.ValueCallback
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import java.net.URL

class Posts(){
    private var postList:List<Post>? = null

    fun addPost(){
        Global.db.collection("post").add(Post("title",System.currentTimeMillis(), GlobalLogin.getUserData()!!.id, false, 0, 0, null))
    }

    fun setPosts(){

    }
    fun getPosts(callback: OnDataReceivedInterface){
        Global.db.collection("post")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
                postList = result.toObjects(Post::class.java)
                callback.onDataReceived(postList!!)
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }
}

data class Post(
    val title: String? = null,
    val time: Long? = null,
    val user: Long? = null,
    val isNotice: Boolean? = null,
    val commentCount: Int? = null,
    val favoriteCount: Int? = null,
    val imageUrl: URL? = null
)

interface OnDataReceivedInterface {
    fun onDataReceived(data:List<Post>)
}