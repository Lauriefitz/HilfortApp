package com.example.archaeologyfieldwork.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import com.example.archaeologyfieldwork.helpers.checkLocationPermissions
import com.example.archaeologyfieldwork.helpers.createDefaultLocationRequest
import com.example.archaeologyfieldwork.helpers.isPermissionGranted
import com.example.archaeologyfieldwork.helpers.showImagePicker
import com.example.archaeologyfieldwork.models.Location
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.VIEW
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HillfortPresenter(view: BaseView) : BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    var hillfort = MainModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false

    var map: GoogleMap? = null
    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable("hillfort_edit")!!
            view.showHillfort(hillfort)

        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    override fun doRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }

    fun doAddOrSave(title: String, description: String, rating: Float, favourite: Boolean) {
        hillfort.title = title
        hillfort.description = description
        hillfort.rating = rating
        hillfort.favourite = favourite
        doAsync {
            if (edit) {
                app.hillforts.update(hillfort)
            } else {
                app.hillforts.create(hillfort)
            }
            uiThread {
                view?.finish()
                view?.navigateTo(VIEW.LIST)
            }
        }
    }


    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.hillforts.delete(hillfort)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        view?.navigateTo(
            VIEW.LOCATION,
            LOCATION_REQUEST,
            "location",
            Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom)
        )
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view?.showImage(hillfort.image)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.location = location
                locationUpdate(location)
            }
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location)
    }

    fun locationUpdate(location: Location) {
        hillfort.location = location
        hillfort.location.zoom = 15f
        map?.clear()
        val options = MarkerOptions().title(hillfort.title)
            .position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    hillfort.location.lat,
                    hillfort.location.lng
                ), hillfort.location.zoom
            )
        )
        view?.showLocation(hillfort.location)
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun doShare(){
        view?.onShareClicked(hillfort)
    }
}
