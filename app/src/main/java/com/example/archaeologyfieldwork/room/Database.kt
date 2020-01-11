package com.example.archaeologyfieldwork.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.archaeologyfieldwork.models.MainModel

@Database(entities = arrayOf(MainModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun hillfortDao(): HillfortDao
}
