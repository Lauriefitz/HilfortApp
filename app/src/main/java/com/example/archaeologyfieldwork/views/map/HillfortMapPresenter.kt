package com.example.archaeologyfieldwork.views.map

import com.example.archaeologyfieldwork.main.MainApp
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HillfortMapPresenter(view: BaseView) : BasePresenter(view) {

    init {
        app = view.application as MainApp
    }

    fun doPopulateMap(map: GoogleMap, hillforts: List<MainModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        hillforts.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val hillfort = marker.tag as MainModel
        view?.showHillfort(hillfort)
    }

    fun loadHillforts() {
        view?.showHillforts(app.hillforts.findAll()!!)
    }

}
