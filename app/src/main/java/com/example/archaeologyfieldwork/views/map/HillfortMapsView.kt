package com.example.archaeologyfieldwork.views.map

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.helpers.readImageFromPath
import com.example.archaeologyfieldwork.main.MainApp
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.map.HillfortMapPresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.android.synthetic.main.activity_hillfort_maps.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import kotlinx.android.synthetic.main.activity_main.mapView as mapView1

class HillfortMapsView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HillfortMapPresenter
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_maps)
        this.init(toolbar, true)

        presenter = initPresenter (HillfortMapPresenter(this)) as HillfortMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadHillforts()
        }
    }

    override fun showHillfort(hillfort: MainModel) {
        currentTitle.text = hillfort.title
        currentDescription.text = hillfort.description
        //currentImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        Glide.with(this).load(hillfort.image).into(currentImage)
    }

    override fun showHillforts(hillforts: List<MainModel>) {
        presenter.doPopulateMap(map, hillforts)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}

