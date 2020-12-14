package com.ishnn.labtools.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ishnn.labtools.MainActivity
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : Fragment(), IOnBackPressed {
    private lateinit var mTextView: TextView
    private lateinit var kakao_loginbtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView: TextView = root.findViewById(R.id.text_profile)
        mTextView = textView
        kakao_loginbtn = root.findViewById(R.id.kakao_loginbtn)
        kakao_loginbtn.setOnClickListener(View.OnClickListener {
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                } else if (token != null) {
                    requireActivity().runOnUiThread {
                        Log.e("kakao", "로그인 되어있음")
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                            }
                            else if (user != null) {
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                                kakao_loginbtn.visibility = View.GONE
                                mTextView.visibility = View.VISIBLE
                                mTextView.text = user.kakaoAccount?.profile?.nickname
                            }
                        }
                    }
                }
            }
            if (LoginClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                LoginClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
            } else {
                LoginClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        })
        return root
    }

    override fun onBackPressed(): Boolean {
        Log.e("a","b")
        return false
    }
}