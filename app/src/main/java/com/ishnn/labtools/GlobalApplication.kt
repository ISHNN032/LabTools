package com.ishnn.labtools

import android.app.Activity
import android.app.Application
import android.util.Log
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