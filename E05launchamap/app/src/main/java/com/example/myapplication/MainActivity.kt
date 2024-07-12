package com.example.myapplication
//package com.example.e05launchamap

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var latitudeEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var showMapButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        latitudeEditText = findViewById(R.id.latEditText)
        longitudeEditText = findViewById(R.id.lngEditText)
        showMapButton = findViewById(R.id.showMapButton)

        showMapButton.setOnClickListener { showMap() }
    }

    private fun showMap() {
        val lat = latitudeEditText.text.toString().toDouble()
        val lng = longitudeEditText.text.toString().toDouble()
        val locationUri = Uri.parse("geo:$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)

        startActivity(mapIntent)
    }
}
