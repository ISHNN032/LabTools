package com.ishnn.labtools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ishnn.labtools.model.TimerItem
import com.kakao.sdk.common.KakaoSdk.init
import com.kakao.sdk.common.util.Utility
import org.json.JSONException
import java.lang.reflect.Type


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
    const val CROPPED_IMAGE = "cropped.jpg"
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

        if (values.isNotEmpty()) {
            val gson = Gson()
            val listType: Type = object : TypeToken<ArrayList<TimerItem?>?>() {}.type
            val jvalue = gson.toJson(values, listType)
            editor.putString(key, jvalue)
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
        var jvalue = ArrayList<TimerItem>()
        if (json != null) {
            try {
                val gson = Gson()
                val listType: Type = object : TypeToken<ArrayList<TimerItem?>?>() {}.type
                jvalue = gson.fromJson<ArrayList<TimerItem>>(json, listType)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return jvalue
    }
}