package com.ishnn.labtools

import android.Manifest
import android.app.Application
import android.content.Context
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
import com.kakao.sdk.common.KakaoSdk
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_community,
                R.id.navigation_calculator,
                R.id.navigation_stopwatch,
                R.id.navigation_memo
            )
        )

        val sharedPref = getSharedPreferences("lan",Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
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

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = fragment?.childFragmentManager?.primaryNavigationFragment
        if (currentFragment.toString().contains("MemoFragment") ||
            currentFragment.toString().contains("HomeFragment")
        ) {
            val webBack = currentFragment as IOnBackPressed
            webBack?.onBackPressed()?.takeIf { !it }?.let {
                Log.e("c", "b")
                super.onBackPressed()
            }
        }
        else{
            Log.e(currentFragment.toString(), "b")
            super.onBackPressed()
        }
    }

    class GlobalApplication : Application() {
        override fun onCreate() {
            super.onCreate()
            // 다른 초기화 코드들

            // Kakao SDK 초기화
            KakaoSdk.init(this, "fa80791b0b8cfd763b4894481f6e8638")
        }
    }
}