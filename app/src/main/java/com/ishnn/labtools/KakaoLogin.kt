package com.ishnn.labtools

import android.content.Context
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

object KakaoLogin {
    private var profile : UserProfile? = null
    interface OnKakaoLoginInterface {
        fun onLogin()
    }
    interface OnKakaoLogoutackInterface {
        fun onLogout()
    }

    fun login(context: Context, callbacks: OnKakaoLoginInterface){
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
                        this.profile = UserProfile(user.id, user.kakaoAccount!!.profile!!.nickname)
                        callbacks.onLogin()
                    }
                }
            }
        }
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun setUserData(user:UserProfile){
        profile = user
    }

    fun getUserData():UserProfile?{
        return profile
    }

    fun getUserLoggedIn():Boolean{
        return profile != null
    }

    fun logout(callbacks: OnKakaoLogoutackInterface){
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Kakao", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("Kakao", "로그아웃 성공. SDK에서 토큰 삭제됨")
                profile = null
                callbacks.onLogout()
            }
        }
    }
}

data class UserProfile(
    var id: Long,
    var nickName: String
)