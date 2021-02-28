package com.dude36.weatherapp

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import java.io.Serializable

class City(
        var icon: String?,
        var cityName: String,
        var cityTempF: Double?,
        var cityTempC: Double?,
        var cityHighF: Double?,
        var cityLowF: Double?,
        var cityHighC: Double?,
        var cityLowC: Double?,
        var cityPrecip: Double?,
        var complete: Boolean = false
) : Serializable {
    override fun toString(): String {
        return "$cityName currently at $cityTempF F or $cityTempC C"
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

/**Convert Kelvin to Celsius
 */
fun KtoC(num: Double) : Double { return num - 273.15 }

/**Convert Kelvin to Fahrenheit
 */
fun KtoF(num: Double) : Double { return KtoC(num) * 9 / 5 + 32 }


class MainActivity : AppCompatActivity() {
    internal var RecyclerView: RecyclerView? = null
    var cityList: MutableList<City>? = null
    internal var cityAdapter: CityAdapter? = null
    internal var inputString: String = ""

    /**Update all Cities in the main list. This spawns 'n' number of threads where 'n' is the number of cities
     */
    fun updateCityData() {
        // Setup threads for all cities in the City List
        val network = NetworkAdapter()
        GlobalScope.async {
            for (i in 0 until cityList!!.size) {
                cityList!![i] = network.getDailyData(cityList!![i].cityName)!!
                cityList!![i].cityPrecip = network.getHourlyData(cityList!![i].cityName)
                cityList!![i].complete = true
                GlobalScope.launch(Dispatchers.Main) {
                    cityAdapter?.notifyDataSetChanged()
                }
            }
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
            cityList?.add(City(null, cityListName, 10.0, -10.0, null, null, null, null, null, false))
        }

        // Send for Data
        updateCityData()

        // Add City Data to the Layout
        cityAdapter = CityAdapter(this@MainActivity, cityList!!)

        val layoutManager = LinearLayoutManager(applicationContext)
        RecyclerView!!.layoutManager = layoutManager
        RecyclerView!!.adapter = cityAdapter

        // New City Functionality to be added here (empty cardView Object)
        val addButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addButton.setOnClickListener { view ->
            val alert = AlertDialog.Builder(this@MainActivity)
            var inputText = EditText(this@MainActivity)
            inputText.inputType = InputType.TYPE_CLASS_TEXT
            alert.setView(inputText)

            alert.setTitle("Enter a City Name")
            alert.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                inputString = inputText.getText().toString()
                cityList?.add(City(null, inputString, 10.0, -10.0, null, null, null, null, null, false))
                updateCityData()
                cityAdapter?.notifyItemInserted((cityList as ArrayList<City>).size - 1)
            })
            alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            alert.show()
        }
    }
}
