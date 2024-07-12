package com.example.e17weatherwear

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
import com.example.e17weatherwear.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Replace these values with the actual latitude and longitude of the location
        val latitude = 60.1699
        val longitude = 24.9384

        fetchWeatherData(latitude, longitude)
    }

    private fun fetchWeatherData(lat: Double, lon: Double) {
        val apiKey = "27a889dfd839aba134637e5448340536"
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey&units=metric"

        binding.progressBar.visibility = View.VISIBLE

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Error fetching weather data", e)
                runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("MainActivity", "Error: Response is not successful. Code: ${response.code}")
                    runOnUiThread {
                        binding.progressBar.visibility = View.GONE
                    }
                    return
                }

                response.body?.string()?.let { responseBody ->
                    Log.d("MainActivity", "Response: $responseBody")
                    val gson = Gson()
                    val weatherApiResponse = gson.fromJson(responseBody, WeatherApiResponse::class.java)
                    val weatherData = WeatherData(
                        weatherApiResponse.name,
                        weatherApiResponse.weather[0].description,
                        "${weatherApiResponse.main.temp}°C"
                    )

                    Log.d("MainActivity", "City: ${weatherData.city}")
                    Log.d("MainActivity", "Description: ${weatherData.description}")
                    Log.d("MainActivity", "Temperature: ${weatherData.temperature}")

                    runOnUiThread {
                        // Set weather info text and scale it to fit the watch screen
                        binding.weatherTextView.text = String.format(
                            resources.getString(R.string.weather_info),
                            weatherData.city,
                            weatherData.description,
                            weatherData.temperature
                        )
                        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                            binding.weatherTextView, 10, 20, 1,
                            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
                        )
                        binding.progressBar.visibility = View.GONE
                    }
                } ?: run {
                    Log.e("MainActivity", "Error: Response body is null")
                    runOnUiThread {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    data class WeatherData(
        val city: String,
        val description: String,
        val temperature: String
    )

    data class WeatherApiResponse(
        val name: String,
        val weather: List<Weather>,
        val main: Main
    )

    data class Weather(
        val description: String
    )

    data class Main(
        val temp: Double
    )
}
