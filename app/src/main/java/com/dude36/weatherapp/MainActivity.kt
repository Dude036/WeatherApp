package com.dude36.weatherapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class City(var icon: Image, var cityName: String, var cityTempF: Float, var cityTempC: Float)

class MainActivity : AppCompatActivity() {
    internal var RecyclerView: RecyclerView? = null
    internal var cityList: MutableList<City>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup View
        RecyclerView = findViewById<RecyclerView>(R.id.Recycler)

        // Add Hard Coded cities

        // Send for Data

        // New City Functionality to be added here (empty cardView Object)
    }
}
