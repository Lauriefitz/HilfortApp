package com.example.archaeologyfieldwork.models

interface hillfortStore {
    fun findAll(): List<MainModel>
    fun findAllFavs():List<MainModel>
    fun create(hillfort: MainModel)
    fun update(hillfort: MainModel)
    fun delete(hillfort: MainModel)
    fun findById(id:Long) : MainModel?
    fun clear()
}