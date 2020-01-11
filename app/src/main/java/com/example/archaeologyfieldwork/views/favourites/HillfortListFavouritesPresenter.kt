package com.example.archaeologyfieldwork.views.favourites

import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HillfortListFavouritesPresenter (view: BaseView) : BasePresenter(view){

    fun doShowFavHillfort(){
        doAsync {
            val hillforts = app.hillforts.findAllFavs()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doEditHillfort(hillfort: MainModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
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
}
