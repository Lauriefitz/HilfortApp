package com.example.archaeologyfieldwork.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.archaeologyfieldwork.models.Location
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.account.AccountView
import com.example.archaeologyfieldwork.views.editlocation.EditLocationView
import com.example.archaeologyfieldwork.views.favourites.HillfortListFavouritesView
import com.example.archaeologyfieldwork.views.hillfort.MainView
import com.example.archaeologyfieldwork.views.hillfortlist.HillfortListView
import com.example.archaeologyfieldwork.views.login.LoginView
import com.example.archaeologyfieldwork.views.map.HillfortMapsView
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, HILLFORT, MAPS, LIST, LOGIN, ACCOUNT, FAVOURITE
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, HillfortListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.HILLFORT -> intent = Intent(this, MainView::class.java)
            VIEW.MAPS -> intent = Intent(this, HillfortMapsView::class.java)
            VIEW.LIST -> intent = Intent(this, HillfortListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.ACCOUNT -> intent = Intent(this, AccountView::class.java)
            VIEW.FAVOURITE -> intent = Intent(this, HillfortListFavouritesView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${title}: ${user.email}"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun onShareClicked(value: Parcelable?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hillfort App")
        val shareMessage = constructEmailTemplate(value as MainModel)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Choose an Application"))
    }

    fun constructEmailTemplate(hillfort: MainModel): String {
        val location =
            "https://www.google.ie/maps/@${hillfort.location.lat},${hillfort.location.lng},10z"

        return "Check out this Hillfort from Hillfort App!\n\n" +
                "Name: ${hillfort.title}\nDescription: ${hillfort.description}\n" +
                "My Rating: ${hillfort.rating}/5 Stars\n\n" +
                "You can find the hillfort here: $location"
    }


    open fun showHillfort(hillfort: MainModel) {}
    open fun showHillforts(hillforts: List<MainModel>) {}
    open fun showImage(uri: String) {}
    open fun showLocation(location : Location) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}