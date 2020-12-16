package com.ishnn.labtools

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.fade_out)

        setContentView(R.layout.activity_login)
        kakao_loginbtn.setOnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("Kakao", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.i("Kakao", "로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("Kakao", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i("Kakao", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                            KakaoLogin.setUserData(UserProfile(user.id, user.kakaoAccount!!.profile!!.nickname))
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}