package com.ishnn.labtools

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

object GlobalLogin {
    private var mProfile: UserProfile? = null
    lateinit var mOAuthLoginInstance: OAuthLogin

    interface OnLoginInterface {
        fun onLogin(platform: LoginPlatform)
        fun onLogout()
    }

    fun initLogin(context: Context) {
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(
            context, "yLP0z2Vu1q0XMIokl4Z_", "KJHOoZZ8lO", "LabTools"
            //,OAUTH_CALLBACK_INTENT
            // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        )
        mOAuthLoginInstance.showDevelopersLog(true)
    }

    fun loginKakao(context: Context, callbacks: OnLoginInterface?) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("Kakao", "로그인 실패", error)
            } else if (token != null) {
                Log.i("Kakao", "로그인 성공 ${token.accessToken}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("Kakao", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            "Kakao", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                        this.mProfile = UserProfile(
                            user.id,
                            user.kakaoAccount!!.profile!!.nickname,
                            //null
                            user.kakaoAccount?.profile?.thumbnailImageUrl
                        )
                        GlobalScope.launch {
                            try {
                                Global.db.collection("user").document(mProfile!!.id.toString()).set(mProfile!!)
                            }catch (e: Exception){
                                e.printStackTrace()
                                Log.e("Firebase error", e.toString())
                            }
                        }
                        callbacks?.onLogin(LoginPlatform.KAKAO)
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

    fun logoutKakao(callbacks: OnLoginInterface) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Kakao", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i("Kakao", "로그아웃 성공. SDK에서 토큰 삭제됨")
                mProfile = null
                callbacks.onLogout()
            }
        }
    }

    fun loginNaver(context: Context, callbacks: OnLoginInterface?) {
        val accessToken: String = mOAuthLoginInstance.getAccessToken(context)
        val refreshToken: String = mOAuthLoginInstance.getRefreshToken(context)
        val expiresAt: Long = mOAuthLoginInstance.getExpiresAt(context)
        val tokenType: String = mOAuthLoginInstance.getTokenType(context)
        Log.e("token", "$accessToken, $refreshToken, $expiresAt, $tokenType")

        val url = "https://openapi.naver.com/v1/nid/me"
        val at: String = mOAuthLoginInstance.getAccessToken(context)
        val callback: (token: String?) -> Unit = {
            try {
                Log.e("response", it!!)
                if (it.isNotEmpty()) {
                    val jsonObject = JSONObject(it)
                    val data: JSONObject = jsonObject.getJSONObject("response")
                    val id = data.getString("id")
                    val name = data.getString("nickname")
                    this.mProfile = UserProfile(id.toLong(), name, null)
                }
            } catch (e: Exception) {
                Log.e("e", e.toString())
            }
        }
        GlobalScope.launch {
            callback(mOAuthLoginInstance.requestApi(context, at, url))
            Global.db.collection("user").document(mProfile!!.id.toString()).set(mProfile!!)
            callbacks?.onLogin(LoginPlatform.NAVER)
        }
    }
    fun getLoginError(context: Context){
        val errorCode: String = mOAuthLoginInstance.getLastErrorCode(context).getCode()
        val errorDesc: String =
            mOAuthLoginInstance.getLastErrorDesc(context)
        Toast.makeText(
            context,
            "errorCode:$errorCode, errorDesc:$errorDesc",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun logoutNaver(context: Context, callbacks: OnLoginInterface) {
        mOAuthLoginInstance.logout(context)
        mProfile = null
        callbacks.onLogout()
    }


    fun getUserData(): UserProfile? {
        return mProfile
    }

    fun getUserLoggedIn(): Boolean {
        return mProfile != null
    }
}

data class UserProfile(
    var id: Long? = null,
    var nickName: String? = null,
    var imageUrl: String? = null
)

enum class LoginPlatform{
    KAKAO, NAVER
}