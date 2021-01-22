package com.yashanand.time2achieve.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yashanand.time2achieve.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val time = findViewById<TextView>(R.id.time)
        val two = findViewById<TextView>(R.id.two)
        val achieve = findViewById<TextView>(R.id.achieve)
        time.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rightmove))
        achieve.startAnimation(AnimationUtils.loadAnimation(this, R.anim.leftmove))
        two.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))


        Handler().postDelayed({
            startActivity(Intent(this, BranchList::class.java))
            finish()
        }, 1500)
    }
}