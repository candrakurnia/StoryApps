package com.project.storyapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.storyapps.databinding.ActivitySplashScreenBinding
import com.project.storyapps.pref.UserPreference

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var preference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashTime : Long = 2000
        preference = UserPreference(this)

        Handler(Looper.getMainLooper()).postDelayed( {
            val isLogin: Boolean = preference.getIsLogin(this@SplashScreenActivity) == true
            if (isLogin) {
                val intent = Intent(this, StoryActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }


        }, splashTime)
    }
}