package com.ishnn.labtools

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL


class LoginActivity : AppCompatActivity() {
    lateinit var mOAuthLoginInstance: OAuthLogin
    lateinit var mContext: Context
    var db = FirebaseFirestore.getInstance()

    interface OnKakaoLoginInterface {
        fun onLogin()
    }

    interface OnKakaoLogoutackInterface {
        fun onLogout()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out)

        setContentView(R.layout.activity_login)

        mContext = this
        Log.e("context", mContext.toString())
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(
            mContext, "yLP0z2Vu1q0XMIokl4Z_", "KJHOoZZ8lO", "LabTools"
            //,OAUTH_CALLBACK_INTENT
            // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        )
        mOAuthLoginInstance.showDevelopersLog(true)
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)

        kakao_loginbtn.setOnClickListener {
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

                            Login.setUserData(
                                UserProfile(
                                    user.id, user.kakaoAccount!!.profile!!.nickname, URL(
                                        user.kakaoAccount!!.profile!!.thumbnailImageUrl
                                    )
                                )
                            )
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

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken: String = mOAuthLoginInstance.getAccessToken(mContext)
                val refreshToken: String = mOAuthLoginInstance.getRefreshToken(mContext)
                val expiresAt: Long = mOAuthLoginInstance.getExpiresAt(mContext)
                val tokenType: String = mOAuthLoginInstance.getTokenType(mContext)
                Log.e("token", "$accessToken, $refreshToken, $expiresAt, $tokenType")
                val callback: (token: String?) -> Unit = {
                    try {
                        Log.e("response", it!!)
                        if (it.isNotEmpty()) {
                            val jsonObject = JSONObject(it)
                            val data: JSONObject = jsonObject.getJSONObject("response")
                            val id = data.getString("id")
                            val name = data.getString("nickname")
                            Login.setUserData(
                                UserProfile(
                                    id.toLong(), name, null
                                )
                            )
                            val intent = Intent(mContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        Log.e("e", e.toString())
                    }
                }
                loginNaver(callback = callback)
            } else {
                val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).getCode()
                val errorDesc: String =
                    mOAuthLoginInstance.getLastErrorDesc(mContext)
                Toast.makeText(
                    mContext,
                    "errorCode:$errorCode, errorDesc:$errorDesc",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun loginNaver(callback: (token: String?) -> Unit) {
        val url = "https://openapi.naver.com/v1/nid/me"
        val at: String = mOAuthLoginInstance.getAccessToken(mContext)
        GlobalScope.launch {
            delay(1000)
            callback(mOAuthLoginInstance.requestApi(mContext, at, url))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun addAccountDB() {
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "addAccountDB",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e ->
                Log.w(
                    "addAccountDB",
                    "Error adding document",
                    e
                )
            }

        // Create a new user with a first, middle, and last name
        val usesr: MutableMap<String, Any> = HashMap()
        usesr["first"] = "Alan"
        usesr["middle"] = "Mathison"
        usesr["last"] = "Turing"
        usesr["born"] = 1912

        db.collection("users")
            .add(usesr)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "addAccountDB",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w("addAccountDB", "Error adding document", e) }
    }
}