package com.gpillaca.mapa19.ui.findme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gpillaca.mapa19.R

class FindMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_me)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frameLayoutContent,
                FindMeFragment.newInstance(),
                FindMeActivity::class.java.simpleName
            )
            .addToBackStack(null)
            .commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
