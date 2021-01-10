package com.ishnn.labtools

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters
import com.google.firebase.dynamiclinks.DynamicLink.SocialMetaTagParameters
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kakao.sdk.common.KakaoSdk.init
import com.kakao.sdk.common.util.Utility


class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        globalApplicationContext = this
        val keyHash: String = Utility.getKeyHash(this)
        Log.e("hash", keyHash)
        init(this, resources.getString(R.string.kakao_app_key))
    }

    companion object {
        @Volatile
        var globalApplicationContext: GlobalApplication? = null
                    private set

        // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
        @Volatile
        var currentActivity: Activity? = null
    }
}

object Global {
    const val STORAGE_POST_CONTENT = "post/content/"
    const val CROPPED_IMAGE = "cropped.png"

    var db = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance("gs://labtools-59b01.appspot.com")

    fun hideKeyboard(activity: Activity){
        val imm: InputMethodManager = activity.getSystemService(Application.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}