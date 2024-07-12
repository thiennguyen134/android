package com.example.e12golfcoursesclustered

import android.content.Context
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer

class MarkerClusterRenderer(
    private val context: Context,
    private val mapView: MapView,
    private val clusterGroup: RadiusMarkerClusterer
) {

    fun addItems(items: List<GolfCourseItem>) {
        for (item in items) {
            val marker = Marker(mapView)
            marker.position = item.position
            marker.title = item.title
            //marker.snippet = item.snippet
            //marker.icon = Marker.createDefaultIcon(context, item.color)
            marker.relatedObject = item
            clusterGroup.add(marker)
        }
    }
}
