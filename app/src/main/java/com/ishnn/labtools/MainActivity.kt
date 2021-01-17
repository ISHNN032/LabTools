package com.ishnn.labtools

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.ishnn.labtools.ui.community.post.PostManager
import com.ishnn.labtools.util.IOnBackPressed
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.fade_out)

        GlobalApplication.currentActivity = this
        autoLogin()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_community,
                R.id.navigation_calculator,
                R.id.navigation_stopwatch,
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
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val lastLogin = sharedPref.getString(getString(R.string.shared_last_login_platform), null)
        if (lastLogin != null) {
            when (lastLogin) {
                LoginPlatform.NAVER.name -> {
                    GlobalLogin.loginNaver(this, null)
                }
                LoginPlatform.KAKAO.name -> {
                    GlobalLogin.loginKakao(this, null)
                }
            }
        }
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = fragment?.childFragmentManager?.primaryNavigationFragment
        if (currentFragment.toString().contains("MemoFragment") ||
            currentFragment.toString().contains("CommunityFragment")
        ) {
            val webBack = currentFragment as IOnBackPressed
            webBack.onBackPressed().takeIf { !it }?.let {
                Log.e("c", "b")
                super.onBackPressed()
            }
        } else {
            Log.e(currentFragment.toString(), "b")
            super.onBackPressed()
        }
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