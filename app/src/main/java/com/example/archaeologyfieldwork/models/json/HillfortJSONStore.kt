package com.example.archaeologyfieldwork.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import com.example.archaeologyfieldwork.helpers.*
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.models.hillfortStore
import org.jetbrains.anko.info
import java.util.*
import kotlin.collections.ArrayList

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<MainModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class hillfortJSONStore : hillfortStore, AnkoLogger {

    val context: Context
    var hillforts = mutableListOf<MainModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MainModel> {
        return hillforts
    }

    override fun findAllFavs(): List<MainModel> {
        return hillforts.filter { it.favourite }
    }

    override fun create(hillfort: MainModel) {
        hillfort.id = generateRandomId()
        hillforts.add(hillfort)
        serialize()
    }


    override fun update(hillfort: MainModel) {
        val hillfortList = findAll() as ArrayList<MainModel>
        var foundhillfort: MainModel? = hillfortList.find { p->p.id == hillfort.id }
        if(foundhillfort != null){
            foundhillfort.title = hillfort.title
            foundhillfort.description = hillfort.description
            foundhillfort.image = hillfort.image
            foundhillfort.location = hillfort.location
            foundhillfort.rating = hillfort.rating
            foundhillfort.favourite = hillfort.favourite
            logAll()
        }
        serialize()
    }

    override fun delete(hillfort: MainModel){
        hillforts.remove(hillfort)
        serialize()
    }

    fun logAll(){
        hillforts.forEach{info("${it}")}
    }

    override fun findById(id:Long) : MainModel? {
        val foundHillfort: MainModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hillforts,
            listType
        )
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString,
            listType
        )
    }

    override fun clear() {
        hillforts.clear()
    }
}
