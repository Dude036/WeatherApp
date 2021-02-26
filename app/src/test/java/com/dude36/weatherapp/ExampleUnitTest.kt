package com.dude36.weatherapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.junit.Test

import org.junit.Assert.*
import java.io.File
import java.nio.file.Paths

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun urlifyTest() {
        val main = MainActivity()
        assertEquals(main.urlifyCity("   A  "), "A")
        assertEquals(main.urlifyCity("   A B  "), "A%20B")
    }

    @Test
    fun cityTest() {
        val main = MainActivity()
        main.cityList = ArrayList<City>()
        (main.cityList as ArrayList<City>).add(City(null, "Salt Lake City", null, null))

        main.updateCityData()

        Thread.sleep(1000)

        assertNotNull((main.cityList as ArrayList<City>)[0].cityTempF)
        assertNotNull((main.cityList as ArrayList<City>)[0].cityTempC)
    }
}