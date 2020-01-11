package com.example.archaeologyfieldwork.views.hillfortlist

import android.app.Activity
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.*

class HillfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: MainModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun doAccountView(){
        view?.navigateTo(VIEW.ACCOUNT)
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doShowFavHillfort(){
        view?.navigateTo(VIEW.FAVOURITE)
    }

}
