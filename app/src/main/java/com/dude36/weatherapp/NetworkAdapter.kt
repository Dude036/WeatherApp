package com.dude36.weatherapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class NetworkAdapter {
    /**Helper function for updating city data
     * @param city: String      City name to create URL safe name
     * @return String           URL Safe city Name
     */
    fun urlifyCity(city: String) : String {
        var newCity = city.trim()
        val re = Regex(" ")
        newCity = re.replace(newCity, "%20")
        return newCity
    }

    fun getIcon(code: String?) : Bitmap? {
        var bitty: Bitmap? = null
        val url = URL("http://openweathermap.org/img/wn/" + code + "@2x.png")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            // In thread, Read buffered fytes
            val inStream = BufferedInputStream(urlConnection.inputStream)
            bitty = BitmapFactory.decodeStream(inStream)
        } finally {
            urlConnection.disconnect()
        }
        return bitty
    }

    /**The main legs of the City updates. Handles Network connection to get data for a City Given Name
     * @param city: City        A City to update information on
     */
    fun getDailyData(name: String) : City? {
        val city = City(null, name, null, null, null, null, null, null, null, false)

        val url = URL("http://api.openweathermap.org/data/2.5/weather?q=" + urlifyCity(city.cityName!!) + "&appid=da65fafb6cb9242168b7724fb5ab75e7")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            // In thread, Read buffered fytes
            val inStream = BufferedInputStream(urlConnection.inputStream)
            val contents = ByteArray(1024)
            var bytesRead = 0
            var fullString = ""
            // While there is stuff to be read, dump in to content, and append to full string
            while (inStream.read(contents).also { bytesRead = it } !== -1) {
                fullString += String(contents, 0, bytesRead)
            }

            // Send to Parser
            val gson = Gson()
            var inData = gson.fromJson<OpenWeatherMapData>(fullString, OpenWeatherMapData::class.java)

            // Update City info
            city.icon = inData.weather[0].icon
            city.cityTempF = inData.main["temp"]?.let { KtoF(it) }
            city.cityTempC = inData.main["temp"]?.let { KtoC(it) }
            city.cityHighF = inData.main["temp_max"]?.let { KtoF(it) }
            city.cityHighC = inData.main["temp_max"]?.let { KtoC(it) }
            city.cityLowF = inData.main["temp_min"]?.let { KtoF(it) }
            city.cityLowC = inData.main["temp_min"]?.let { KtoC(it) }
        } finally {
            urlConnection.disconnect()

            // Notify Adapter
            // TODO (Josh): Can't update unless in main thread. Workaround?
            // cityAdapter?.notifyDataSetChanged()
        }
        return city
    }

    fun getHourlyData(city: String) : Double? {
        var rainChance: Double?

        val url = URL("http://api.openweathermap.org/data/2.5/forecast?q=" + urlifyCity(city) + "&appid=da65fafb6cb9242168b7724fb5ab75e7")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        try {
            // In thread, Read buffered fytes
            val inStream = BufferedInputStream(urlConnection.inputStream)
            val contents = ByteArray(1024)
            var bytesRead = 0
            var fullString = ""

            // While there is stuff to be read, dump in to content, and append to full string
            while (inStream.read(contents).also { bytesRead = it } !== -1) {
                fullString += String(contents, 0, bytesRead)
            }

            // Regex to Parse
            // Match the number only, Including Decimals and whole numbers
            val re = Regex("\"pop\":([0-9]+[.]?[0-9]*)")
            val result = re.find(fullString, 0)

            // Update City info
            rainChance = result?.groupValues?.get(1)?.toDouble()
        } finally {
            urlConnection.disconnect()

            // Notify Adapter
            // TODO (Josh): Can't update unless in main thread. Workaround?
            // cityAdapter?.notifyDataSetChanged()
        }

        return rainChance
    }
}