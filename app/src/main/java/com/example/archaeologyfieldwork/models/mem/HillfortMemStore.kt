package com.example.archaeologyfieldwork.models.mem

import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.models.hillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class hillfortMemStore : hillfortStore, AnkoLogger {

    val hillforts = ArrayList<MainModel>()

    override fun findAll(): ArrayList<MainModel> {
        return hillforts
    }

    override fun findAllFavs(): List<MainModel> {
        return hillforts.filter { it.favourite }
    }

    override fun create(hillfort: MainModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun update(hillfort: MainModel){
        var foundhillfort: MainModel? = hillforts.find { p->p.id == hillfort.id }
        if(foundhillfort != null){
            foundhillfort.title = hillfort.title
            foundhillfort.description = hillfort.description
            foundhillfort.image = hillfort.image
            foundhillfort.location = hillfort.location
            foundhillfort.rating = hillfort.rating
            foundhillfort.favourite = hillfort.favourite
            logAll()
        }
    }

    override fun delete(hillfort: MainModel){
        hillforts.remove(hillfort)
    }

    fun logAll(){
        hillforts.forEach{info("${it}")}
    }

    override fun findById(id:Long) : MainModel? {
        val foundHillfort: MainModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    override fun clear() {
        hillforts.clear()
    }
}
