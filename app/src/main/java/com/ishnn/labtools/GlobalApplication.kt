package com.ishnn.labtools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager
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
import com.rnnzzo.uxdesign.model.TimerItem
import org.json.JSONArray
import org.json.JSONException


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
    const val IMAGE_TAG = "#IMAGE:"

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

    fun setStringArrayPref(
        context: Context?,
        key: String,
        values: ArrayList<TimerItem>
    ) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        val a = JSONArray()
        for (i in 0 until values.size) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            editor.putString(key, a.toString())
        } else {
            editor.putString(key, null)
        }
        editor.apply()
    }

    fun getStringArrayPref(
        context: Context?,
        key: String
    ): ArrayList<TimerItem>? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString(key, null)
        val urls = ArrayList<TimerItem>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val url = a.get(i) as TimerItem
                    urls.add(url)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return urls
    }
}