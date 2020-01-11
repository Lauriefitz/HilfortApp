package com.example.archaeologyfieldwork.views.editlocation

import android.app.Activity
import android.content.Intent
import com.example.archaeologyfieldwork.main.MainApp
import com.example.archaeologyfieldwork.models.Location
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.hillfortlist.hillfortListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class EditLocationPresenter(view: BaseView) : BasePresenter(view) {

    var location = Location()

    init {
        app = view.application as MainApp
        location = view.intent.extras?.getParcelable<Location>("location")!!
    }

    fun doConfigureMap(map: GoogleMap) {
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Hillfort")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
       // view?.showLocation(Location(loc.latitude, loc.longitude))
    }

    fun doUpdateLocation(lat: Double, lng: Double) {
        location.lat = lat
        location.lng = lng
    }

    fun doSave() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        view?.setResult(Activity.RESULT_OK, resultIntent)
        view?.finish()
    }
    fun doCancel() {
        view?.finish()
    }

    fun doUpdateMarker(marker: Marker) {
        val loc = LatLng(location.lat, location.lng)
        marker.setSnippet("GPS : " + loc.toString())
    }

}
