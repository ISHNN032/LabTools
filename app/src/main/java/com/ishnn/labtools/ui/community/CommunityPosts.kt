package com.ishnn.labtools.ui.community

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.local.ReferenceSet
import com.ishnn.labtools.Global
import com.ishnn.labtools.GlobalLogin
import com.ishnn.labtools.UserProfile
import com.ishnn.labtools.model.PostItem
import java.util.*

class CommunityPosts() {
    fun addPost() {
        Global.db.collection("post").add(
            PostItem(
                "title",
                Date(System.currentTimeMillis()),
                GlobalLogin.getUserData()!!.id,
                false,
                0,
                0,
                null
            )
        )
    }

    fun getPosts(callback: (List<PostItem>) -> Unit) {
        Global.db.collection("post").orderBy("time", Query.Direction.DESCENDING)
            .get(Source.CACHE)
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

    fun getFavorites(callback: (PostItem) -> Unit) {
        if(GlobalLogin.getUserData() == null) return

//        val ref = Global.db.collection("post").document("2nSmnvB49RqQ0rou2Vcx")
//        val ref2 = Global.db.collection("post").document("4gt7pyzqTkqnWqvtz2DQ")
//        val data = hashMapOf("favorites" to listOf<DocumentReference>(ref, ref2))
//        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString()).set(data)

        Global.db.collection("userData").document(GlobalLogin.getUserData()!!.id.toString()).get()
            .addOnSuccessListener { result ->
                val list = result.get("favorites") as List<DocumentReference>
                for(data in list){
                    data.get().addOnSuccessListener {
                        callback(it.toObject(PostItem::class.java)!!)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
    }

    fun getNotices(callback: (List<PostItem>) -> Unit) {
        Global.db.collection("post").orderBy("time", Query.Direction.DESCENDING)
            .whereEqualTo("notice", true)
            .get(Source.CACHE)
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
}