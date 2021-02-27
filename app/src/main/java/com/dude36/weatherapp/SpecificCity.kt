package com.dude36.weatherapp

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

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

        Thread.sleep(1000)
        // Icon and Name
        findViewById<ImageView>(R.id.bigImage).setImageBitmap(bitmap)
        findViewById<TextView>(R.id.city_name_fill).text = city.cityName

        // Current
        val reg = Regex("[0-9]+.[0-9]{2}")
        var matches = reg.find(city.cityTempF.toString())
        var Temp: String = (matches?.groupValues?.get(0) ?: "???") + " F° / "

        matches = reg.find(city.cityTempC.toString())
        Temp += (matches?.groupValues?.get(0) ?: "???") + " C°"
        findViewById<TextView>(R.id.city_temp_fill).text = Temp

        // High
        matches = reg.find(city.cityHighF.toString())
        Temp = (matches?.groupValues?.get(0) ?: "???") + " F° / "
        matches = reg.find(city.cityHighC.toString())
        Temp += (matches?.groupValues?.get(0) ?: "???") + " C°"

        findViewById<TextView>(R.id.city_high_fill).text = Temp

        // Low
        matches = reg.find(city.cityLowF.toString())
        Temp = (matches?.groupValues?.get(0) ?: "???") + " F° / "
        matches = reg.find(city.cityLowC.toString())
        Temp += (matches?.groupValues?.get(0) ?: "???") + " C°"

        findViewById<TextView>(R.id.city_low_fill).text = Temp

        // Precipitation
        val p = (city.cityPrecip?.times(100.0)).toString()
        findViewById<TextView>(R.id.city_precip_fill).text = p
    }
}