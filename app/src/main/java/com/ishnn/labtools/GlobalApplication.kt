package com.ishnn.labtools

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.firestore.FirebaseFirestore
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
    var db = FirebaseFirestore.getInstance()

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