package com.example.e10_favourite_cities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    data class City(val name: String, val latitude: Double, val longitude: Double)

    private val cities = arrayOf(
        City("Helsinki", 60.1699, 24.9384),
        City("Oulu", 65.0121, 25.4650),
        City("Jyväskylä", 62.2415, 25.7209),
        City("Tampere", 61.4982, 23.7608),
        City("Turku", 60.4518, 22.2670)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set user agent to prevent HTTP 403 error from OSM servers
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.map)
        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(true)

        // Add markers for each city
        cities.forEach { city ->
            val marker = Marker(mapView)
            marker.position = GeoPoint(city.latitude, city.longitude)
            marker.title = city.name
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)
        }

        // Calculate the bounding box of the chosen cities
        val minLatitude = cities.minOf { it.latitude }
        val maxLatitude = cities.maxOf { it.latitude }
        val minLongitude = cities.minOf { it.longitude }
        val maxLongitude = cities.maxOf { it.longitude }
        val boundingBox = BoundingBox(maxLatitude, maxLongitude, minLatitude, minLongitude)

        // Set map view to fit the bounding box
        mapView.post {
            mapView.zoomToBoundingBox(boundingBox, true, 50)
        }
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
