package com.example.myapplication

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class WeatherAppWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        private fun fetchWeatherAndUpdateWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int,
            views: RemoteViews
        ) {
            // Fetch weather data
            val apiKey = "27a889dfd839aba134637e5448340536"
            val url =
                "https://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=$apiKey&units=metric"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("WeatherAppWidget", "Failed to fetch weather data: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val weather = Gson().fromJson(response.body?.string(), WeatherResponse::class.java)
                    if (weather != null) {
                        // Update widget UI
                        views.setTextViewText(R.id.widget_city_name, weather.name)
                        views.setTextViewText(R.id.widget_temperature, "${weather.main.temp}°C")
                        views.setTextViewText(R.id.widget_description, weather.weather[0].description.capitalize())
                        views.setTextViewText(R.id.widget_humidity, "Humidity: ${weather.main.humidity}%")

                        // Update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    } else {
                        Log.e("WeatherAppWidget", "Failed to parse weather data")
                    }
                }
            })
        }

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.weather_app_widget)

            // Fetch weather data and update the widget views
            fetchWeatherAndUpdateWidget(context, appWidgetManager, appWidgetId, views)
        }
    }
}
