package com.dude36.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class City(var icon: ImageView?, var cityName: String?, var cityTempF: Double?, var cityTempC: Double?)

class MainActivity : AppCompatActivity() {
    internal var RecyclerView: RecyclerView? = null
    internal var cityList: MutableList<City>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup View
        RecyclerView = findViewById<RecyclerView>(R.id.Recycler)

        // Add Hard Coded cities
        cityList?.add(City(null, "San Francisco", 0.0, 0.0))

        // Send for Data

        // New City Functionality to be added here (empty cardView Object)
    }
}
