package com.gpillaca.mapa19.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.mapa19.R
import com.gpillaca.mapa19.databinding.ActivityMainBinding
import com.gpillaca.mapa19.ui.map.FindMeFragment
import com.gpillaca.mapa19.ui.map.MapFragment

class MainActivity : AppCompatActivity(), MapFragment.ActionListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.frameLayoutContent, MapFragment.newInstance(), MapFragment::class.java.simpleName)
            .show(MapFragment.newInstance())
            .commit()
    }

    override fun navigateToFindMe() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayoutContent, FindMeFragment.newInstance())
            .addToBackStack(MapFragment::class.java.simpleName)
            .commit()
    }
}