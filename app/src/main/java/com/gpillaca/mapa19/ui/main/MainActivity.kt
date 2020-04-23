package com.gpillaca.mapa19.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.databinding.ActivityMainBinding
import com.gpillaca.mapa19.ui.findme.FindMeActivity
import com.gpillaca.mapa19.ui.map.MapFragment

class MainActivity : AppCompatActivity(), MapFragment.ActionListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frameLayoutContent,
                MapFragment.newInstance(),
                MapFragment::class.java.simpleName
            )
            .addToBackStack(null).commit()

    }

    override fun navigateToFindMe() {
        startActivity(Intent(this, FindMeActivity::class.java))
    }
}