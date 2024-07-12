package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import android.graphics.drawable.BitmapDrawable


class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    private val url = "https://ptm.fi/materials/golfcourses/golf_courses.json"

    private lateinit var golfCourses: JSONArray
    private val courseTypes: Map<String, Int> = mapOf(
        "?" to Color.MAGENTA,
        "Etu" to Color.BLUE,
        "Kulta" to Color.GREEN,
        "Kulta/Etu" to Color.YELLOW
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize osmdroid configuration
        Configuration.getInstance()
            .load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        mapView = findViewById(R.id.map)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(5.0)

        loadData()
    }

    private fun loadData() {
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                golfCourses = response.getJSONArray("courses")

                for (i in 0 until golfCourses.length()) {
                    val course = golfCourses.getJSONObject(i)
                    val lat = course["lat"].toString().toDouble()
                    val lng = course["lng"].toString().toDouble()
                    val geoPoint = GeoPoint(lat, lng)
                    val type = course["type"].toString()
                    val title = course["course"].toString()
                    val address = course["address"].toString()
                    val phone = course["phone"].toString()
                    val email = course["email"].toString()
                    val webUrl = course["web"].toString()

                    if (courseTypes.containsKey(type)) {
                        val color = courseTypes[type] ?: Color.RED
                        val marker = createColoredMarker(color)
                        marker.position = geoPoint
                        marker.title = title
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                       // marker.setOnMarkerClickListener { _, _ -> false }

                        val list = listOf(address, phone, email, webUrl)
                        marker.relatedObject = list
                        marker.infoWindow = CustomInfoWindow(layoutInflater, marker, mapView)


                        mapView.overlays.add(marker)
                    } else {
                        Log.d("GolfCourses", "This course type does not exist in evaluation $type")
                    }
                }

                mapView.controller.setCenter(GeoPoint(65.5, 26.0))

                mapView.invalidate() // Refresh the mapView
            },
            { error ->
                Log.e("GolfCourses", "Error loading JSON: $error")
            }
        )

        queue.add(jsonObjectRequest)
    }

    private fun createColoredMarker(color: Int): Marker {
        val marker = Marker(mapView)
        val icon = Bitmap.createBitmap(24, 48, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(icon)
        val paint = Paint()
        paint.color = color
        canvas.drawCircle(12f, 24f, 12f, paint)
        marker.icon = BitmapDrawable(resources, icon)
        return marker
    }

    private inner class CustomInfoWindow(
        inflater: LayoutInflater,
        marker: Marker,
        mapView: MapView
    ) : InfoWindow(R.layout.activity_maps, mapView) {
        private val mInflater = inflater
        private val mMarker = marker

        override fun onOpen(item: Any?) {
            val titleTextView = mView.findViewById<TextView>(R.id.titleTextView)
            val addressTextView = mView.findViewById<TextView>(R.id.addressTextView)
            val phoneTextView = mView.findViewById<TextView>(R.id.phoneTextView)
            val emailTextView = mView.findViewById<TextView>(R.id.emailTextView)
            val webTextView = mView.findViewById<TextView>(R.id.webTextView)

            titleTextView.text = mMarker.title
            val data = mMarker.relatedObject as List<String>
            addressTextView.text = data[0]
            phoneTextView.text = data[1]
            emailTextView.text = data[2]
            webTextView.text = data[3]
        }

        override fun onClose() {
            // Add any necessary cleanup code here
        }
    }
}
