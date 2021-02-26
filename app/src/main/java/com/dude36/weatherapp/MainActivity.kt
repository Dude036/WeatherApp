package com.dude36.weatherapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class City(
    var icon: ImageView?,
    var cityName: String?,
    var cityTempF: Double?,
    var cityTempC: Double?
)

class MainActivity : AppCompatActivity() {
    internal var RecyclerView: RecyclerView? = null
    internal var cityList: MutableList<City>? = null
    internal var cityAdapter: CityAdapter? = null

    /**Helper function for updating city data
     * @param city: String      City name to create URL safe name
     * @return String           URL Safe city Name
     */
    private fun urlifyCity(city: String) : String {
        var newCity = city.trim()
        val re = Regex(" ")
        newCity = re.replace(newCity, "%20")
        return newCity
    }

    /**Update all Cities in the main list. This spawns 'n' number of threads where 'n' is the number of cities
     */
    fun updateCityData() {
        // Setup threads for all cities in the City List
        for (city in cityList!!) {
            thread(true) {
                updateCity(city)
            }
        }
    }

    /**The main legs of the City updates. Handles Network connection to get data for a City Given Name
     * @param city: City        A City to update information on
     */
    private fun updateCity(city: City) {
        val url = URL("http://api.openweathermap.org/data/2.5/weather?q=" + urlifyCity(city.cityName!!) + "&appid=da65fafb6cb9242168b7724fb5ab75e7")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            // In thread, Read buffered fytes
            val inStream = BufferedInputStream(urlConnection.inputStream)
            var contents = ByteArray(1024)
            var bytesRead = 0
            var fullString = ""
            // While there is stuff to be read, dump in to content, and append to full string
            while (`inStream`.read(contents).also { bytesRead = it } !== -1) {
                fullString += String(contents, 0, bytesRead)
            }

            // Send to Parser
            println(fullString)

            // Update City info
        } finally {
            urlConnection.disconnect()
        }
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
