package com.dude36.weatherapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class City(
    var icon: Bitmap?,
    var cityName: String?,
    var cityTempF: Double?,
    var cityTempC: Double?
) {
    override fun toString(): String {
        return "$cityName currently at $cityTempF F or  $cityTempC C"
    }
}

/**Gson Fillable information
 * @param coord: Map<String, Double>        Coordinates
 * @param weather: List<Weather>            List of Weather Data
 * @param main: Map<String, Double>         Temp Data
 */
class OpenWeatherMapData(
    val coord: Map<String, Double>,
    val weather: List<Weather>,
    val main: Map<String, Double>
) {
    override fun toString(): String {
        return "$coord $weather $main"
    }
}

/**Data helpers for OpenWeatherMapData
 * @param id: int                           Identifier
 * @param main: STring                      Sky Type
 * @param description: String               Simple Description
 * @param icon: string                      Icon Identifier
 */
class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {
    override fun toString(): String {
        return "$id $main $description $icon"
    }
}


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

    /**Convert Kelvin to Celsius
     */
    private fun KtoC(num: Double) : Double { return num - 273.15 }

    /**Convert Kelvin to Fahrenheit
     */
    private fun KtoF(num: Double) : Double { return KtoC(num) * 9 / 5 + 32 }

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
            val gson = Gson()
            val inData = gson.fromJson<OpenWeatherMapData>(fullString, OpenWeatherMapData::class.java)

            // Update City info
            city.cityTempC = inData.main["temp"]?.let { KtoC(it) }
            city.cityTempF = inData.main["temp"]?.let { KtoF(it) }
            println(city)

            // Get Icon
            getIcon(city, inData.weather[0].icon)
        } finally {
            urlConnection.disconnect()

            // Notify Adapter
            // TODO (Josh): Can't update unless in main thread. Workaround?
            // cityAdapter?.notifyDataSetChanged()
        }
    }

    fun getIcon(city: City, code: String) {
        val url = URL("http://openweathermap.org/img/wn/" + code + "@2x.png")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            // In thread, Read buffered fytes
            val inStream = BufferedInputStream(urlConnection.inputStream)
            city.icon = BitmapFactory.decodeStream(inStream)
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup View
        RecyclerView = findViewById<RecyclerView>(R.id.Recycler)

        // Add Hard Coded cities
        cityList = ArrayList<City>()
        for (cityListName in listOf("San Francisco", "New York City", "Salt Lake City")) {
            cityList?.add(City(null, cityListName, 10.0, -10.0))
        }

        // Send for Data
        updateCityData()

        // Sleep until threads are done?
        var ongoing = true
        while (ongoing) {
            ongoing = false
            for (city in cityList as ArrayList<City>) {
                if (city.icon === null) {
                    ongoing = true
                }
            }
        }

        // Add City Data to the Layout
        cityAdapter = CityAdapter(this@MainActivity, cityList!!)

        val layoutManager = LinearLayoutManager(applicationContext)
        RecyclerView!!.layoutManager = layoutManager
        RecyclerView!!.adapter = cityAdapter

        // New City Functionality to be added here (empty cardView Object)
    }
}
