package com.dude36.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class City(var icon: ImageView?, var cityName: String?, var cityTempF: Double?, var cityTempC: Double?)

class MainActivity : AppCompatActivity() {
    internal var RecyclerView: RecyclerView? = null
    internal var cityList: MutableList<City>? = null
    internal var cityAdapter: CityAdapter? = null

    private fun urlifyCity(city: String) : String {
        var newCity = city.trim()
        val re = Regex(" ")
        newCity = re.replace(newCity, "%20")
        return newCity
    }

    fun updateCityData() {
        for (city in cityList!!) {
            updateCity(city)
        }
    }

    private fun updateCity(city: City) {
        val url = "api.openweathermap.org/data/2.5/weather?q=" + urlifyCity(city.cityName!!) + "&appid=da65fafb6cb9242168b7724fb5ab75e7"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup View
        RecyclerView = findViewById<RecyclerView>(R.id.Recycler)

//        for (cityListName in listOf("San Francisco", "New York City", "Salt Lake City")) {
//        }
        // Add Hard Coded cities
        cityList = ArrayList<City>()
        cityList?.add(City(null, "San Francisco", 10.0, -10.0))
        cityList?.add(City(null, "New York City", 10.0, -10.0))
        cityList?.add(City(null, "Salt Lake City", 10.0, -10.0))

        // Send for Data
        updateCityData()

        // Add City Data to the Layout
        cityAdapter = CityAdapter(this@MainActivity, cityList!!)

        val layoutManager = LinearLayoutManager(applicationContext)
        RecyclerView!!.layoutManager = layoutManager
        RecyclerView!!.adapter = cityAdapter

        // New City Functionality to be added here (empty cardView Object)
    }
}
