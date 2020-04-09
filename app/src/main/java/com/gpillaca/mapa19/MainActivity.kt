package com.gpillaca.mapa19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.mapa19.map.MapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutContent, MapFragment.newInstance())
            .commit()
    }
}