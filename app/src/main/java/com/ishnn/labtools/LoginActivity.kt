package com.ishnn.labtools

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), GlobalLogin.OnLoginInterface {
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)

        setContentView(R.layout.activity_login)
        mContext = this
        Log.e("context", mContext.toString())

        GlobalLogin.initLogin(this)

        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)

        kakao_loginbtn.setOnClickListener {
            GlobalLogin.loginKakao(this, this)
        }
    }

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                GlobalLogin.loginNaver(mContext, this@LoginActivity)
            } else {
                GlobalLogin.getLoginError(mContext)
            }
        }
    }

    override fun onLogin(platform: LoginPlatform) {
        Global.setStringPref(this, getString(R.string.shared_last_login_platform), platform.name)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLogout() {
        Global.setStringPref(this, getString(R.string.shared_last_login_platform), "")
    }
}