package com.example.archaeologyfieldwork.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton
import com.bumptech.glide.Glide
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.main.MainApp
import com.example.archaeologyfieldwork.models.Location
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BaseView
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_main.mapView

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = MainModel()
    lateinit var app: MainApp
    lateinit var map: GoogleMap
    var favouriteTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.init(toolbarAdd, true)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }




        //HillfortLocation.setOnClickListener { presenter.doSetLocation() }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

    }

    override fun showHillfort(hillfort: MainModel) {
        hillfortTitle.setText(hillfort.title)
        hillfortDescription.setText(hillfort.description)
        simpleRatingBar.rating = hillfort.rating
        favourite.setChecked(hillfort.favourite)

        //hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        Glide.with(this).load(hillfort.image).into(hillfortImage)
        if(hillfortImage != null){
            chooseImage.setText(R.string.button_changeImage)
        }
        this.showLocation(hillfort.location)
    }

    @SuppressLint("SetTextI18n")
    override fun showLocation(location: Location) {
        hillfortLat.setText("%.6f".format(location.lat))
        hillfortLng.setText("%.6f".format(location.lng))
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
//        if (presenter.edit) {
//            menu.getItem(0).setVisible(true)
//        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.toast)
                } else {
                    presenter.doAddOrSave(hillfortTitle.text.toString(), hillfortDescription.text.toString(), simpleRatingBar.rating, favouriteTrue)
                }
            }
            R.id.item_share -> {
                presenter.doShare()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.favourite -> {
                    if (checked) {
                        favouriteTrue = true
                    } else {
                        toast("Error")
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
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
        presenter.doRestartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    // seperate function to show changed image
    override fun showImage(uri: String) {
        Glide.with(this).load(uri).into(hillfortImage)
    }
}
