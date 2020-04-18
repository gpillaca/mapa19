package com.gpillaca.mapa19.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gpillaca.mapa19.ui.main.MainActivity
import com.gpillaca.mapa19.R

class SplashActivity : AppCompatActivity(), SplashFragment.ActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutContent, SplashFragment.newInstance())
            .commit()
    }

    override fun navigateToActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}