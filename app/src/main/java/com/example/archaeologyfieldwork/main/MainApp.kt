package com.example.archaeologyfieldwork.main

import android.app.Application
import com.example.archaeologyfieldwork.models.UserModel
import com.example.archaeologyfieldwork.models.firebase.HillfortFireStore
import com.example.archaeologyfieldwork.models.json.hillfortJSONStore
import com.example.archaeologyfieldwork.models.hillfortStore
import com.example.archaeologyfieldwork.room.HillfortStoreRoom
import com.google.firebase.FirebaseApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    //val hillforts = ArrayList<MainModel>()
    //val hillforts = hillfortMemStore()
    lateinit var hillforts: hillfortStore
    val users = ArrayList<UserModel>()

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        hillforts = HillfortFireStore(applicationContext)
        info("Hillfort started")
    }
}