package com.example.archaeologyfieldwork.room

import android.content.Context
import androidx.room.Room
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.models.hillfortStore

class HillfortStoreRoom(val context: Context) : hillfortStore {

    var dao: HillfortDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.hillfortDao()
    }

    override fun findAll(): List<MainModel> {
        return dao.findAll()
    }

    override fun findAllFavs(): List<MainModel> {
        return dao.findAllFavs(true)
    }

    override fun findById(id: Long): MainModel? {
        return dao.findById(id)
    }

    override fun create(hillfort: MainModel) {
        dao.create(hillfort)
    }

    override fun update(hillfort: MainModel) {
        dao.update(hillfort)
    }

    override fun delete(hillfort: MainModel) {
        dao.deleteHillfort(hillfort)
    }

    override fun clear() {
    }
}