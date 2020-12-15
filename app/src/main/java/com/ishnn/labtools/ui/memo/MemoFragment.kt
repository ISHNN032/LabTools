package com.ishnn.labtools.ui.memo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.ishnn.labtools.R
import com.ishnn.labtools.util.IOnBackPressed


class MemoFragment : Fragment(), IOnBackPressed {
    private var mWebView // 웹뷰 선언
            : WebView? = null
    private var mWebSettings //웹뷰세팅
            : WebSettings? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_community, container, false)
        // 웹뷰 시작

        // 웹뷰 시작
        mWebView = root.findViewById(R.id.webView) as WebView
        mWebView!!.webViewClient = WebViewClient() // 클릭시 새창 안뜨게
        mWebSettings = mWebView!!.settings //세부 세팅 등록
        mWebSettings!!.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
        mWebSettings!!.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings!!.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings!!.loadWithOverviewMode = true // 메타태그 허용 여부
        mWebSettings!!.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
        mWebSettings!!.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings!!.builtInZoomControls = false // 화면 확대 축소 허용 여부
        mWebSettings!!.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
        mWebSettings!!.cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
        mWebSettings!!.domStorageEnabled = true // 로컬저장소 허용 여부

        mWebView!!.loadUrl("https://m.memo.naver.com/") // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
        return root
    }
    override fun onBackPressed(): Boolean {
        Log.e("a","b")
        return if (mWebView?.canGoBack() == true) {
            Log.e("b","b")
            mWebView?.goBack()
            true
        }else{
            false
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}