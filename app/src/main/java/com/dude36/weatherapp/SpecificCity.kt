package com.dude36.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dude36.weatherapp.ui.main.SpecificCityFragment

class SpecificCity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.specific_city_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SpecificCityFragment.newInstance())
                    .commitNow()
        }
    }
}