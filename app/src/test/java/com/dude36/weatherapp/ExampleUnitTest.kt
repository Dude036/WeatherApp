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
        val main = NetworkAdapter()
        assertEquals(main.urlifyCity("   A  "), "A")
        assertEquals(main.urlifyCity("   A B  "), "A%20B")
    }

    @Test
    fun cityTest() {
        val main = NetworkAdapter()
        val city = main.getDailyData("Salt Lake City")

        Thread.sleep(1000)

        assertNotNull(city)
        if (city != null) {
            assertNotNull(city.cityTempF)
            assertNotNull(city.cityTempC)
            assertNotNull(city.cityHighF)
            assertNotNull(city.cityHighC)
            assertNotNull(city.cityLowF)
            assertNotNull(city.cityLowC)
        }

        assertNotNull(main.getHourlyData("Salt Lake City"))
    }

    @Test
    fun pictureTest() {
        val main = NetworkAdapter()
        var bit: Bitmap? = null
        bit = main.getIcon("10d")
        assertNotNull(bit)
    }
}