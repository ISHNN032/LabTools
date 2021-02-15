package com.ishnn.labtools

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ishnn.labtools.util.IOnBackPressed
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), GlobalLogin.OnLoginInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.fade_out)

        GlobalApplication.currentActivity = this
        autoLogin()

        Global.initLocaldb(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_community,
                R.id.navigation_home,
                R.id.navigation_tools,
                R.id.navigation_memo
            )
        )

        val sharedPref = getSharedPreferences("lan", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("lan", "kor")
            commit()
        }

        try {
            val info = packageManager.getPackageInfo(
                packageName, PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e(
                    "MY KEY HASH:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val REQUEST_CODE = 1
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    private fun autoLogin() {
        val lastLogin = Global.getStringPref(this, getString(R.string.shared_last_login_platform))
        if (lastLogin != null) {
            when (lastLogin) {
                LoginPlatform.NAVER.name -> {
                    GlobalLogin.loginNaver(this, this)
                }
                LoginPlatform.KAKAO.name -> {
                    GlobalLogin.loginKakao(this, this)
                }
            }
        }
        else{
            Log.e("Login", "is null")
        }
    }

    override fun onLogin(platform: LoginPlatform) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLogout() {
        Global.setStringPref(this, getString(R.string.shared_last_login_platform), "")
    }

    override fun onBackPressed() {
//        val fragment =
//            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
//        val currentFragment = fragment?.childFragmentManager?.primaryNavigationFragment
//        if (currentFragment.toString().contains("MemoFragment") ||
//            currentFragment.toString().contains("CommunityFragment")
//        ) {
//            val webBack = currentFragment as IOnBackPressed
//            webBack.onBackPressed().takeIf { !it }?.let {
//                Log.e("c", "b")
//                super.onBackPressed()
//            }
//        } else {
//            Log.e(currentFragment.toString(), "b")
//            super.onBackPressed()
//        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        val request = requestCode.and(0xffff)
//        when(request){
//            PostManager.GET_GALLERY_IMAGE_POST -> {
//                var fragment = supportFragmentManager.findFragmentById(R.id.community_posting)
//                Log.e("resultp", fragment.toString())
//            }
//            PostManager.GET_GALLERY_IMAGE_COMMENT -> {
//                val fragment = supportFragmentManager.findFragmentById(R.id.community_postcontent)
//                Log.e("resultc", fragment.toString())
//            }
//        }
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = fragment?.childFragmentManager?.primaryNavigationFragment
        if (currentFragment.toString().contains("PostingFragment") ||
            currentFragment.toString().contains("PostContentFragment")
        ) {
            Log.e(currentFragment.toString(), "Result ${data?.data}")
            fragment?.onActivityResult(requestCode, resultCode, data)
        } else {
            Log.e(currentFragment.toString(), "has no Result Function")
            super.onBackPressed()
        }
    }
}