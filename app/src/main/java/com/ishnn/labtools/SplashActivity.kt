package com.ishnn.labtools
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animationView = findViewById<View>(R.id.animationView) as LottieAnimationView
        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                startMain()
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
    fun startMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}