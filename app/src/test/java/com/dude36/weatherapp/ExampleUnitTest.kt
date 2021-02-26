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
//    @Test
//    fun UrlifyTest() {
//        MainActivity.urlifyCity()
//    }

    @Test
    fun checkIconDownload() {
        val main = MainActivity()
        var tempCity = City(null, null, null, null)
        main.getIcon(tempCity, "10d")

        val path = Paths.get("").toAbsolutePath().toString() + "/src/main/res/drawable/image.png"
        val fileTest = File(path)
        assertTrue(fileTest.exists())

        val image: Bitmap = BitmapFactory.decodeFile(fileTest.absolutePath)
        while (tempCity.icon === null) {
            Thread.sleep(1000)
        }
    }
}