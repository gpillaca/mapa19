package com.gpillaca.mapa19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gpillaca.mapa19.map.FindMeFragment
import com.gpillaca.mapa19.map.MapFragment

class MainActivity : AppCompatActivity(), MapFragment.ActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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