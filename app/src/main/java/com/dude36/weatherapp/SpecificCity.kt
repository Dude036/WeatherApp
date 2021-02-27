package com.dude36.weatherapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.concurrent.thread

class SpecificCity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_city)

        // Get Data from Intent received
        val city = intent.extras?.get("toFill") as Any
        var bitmap: Bitmap? = null
        val network = NetworkAdapter()

        println(city)
        (city as City).icon?.let { bitmap = network.getIcon(it) }

        println(bitmap.toString())
        Thread.sleep(1000)
        println(city)
    }
}