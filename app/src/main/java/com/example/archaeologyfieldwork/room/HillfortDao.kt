package com.example.archaeologyfieldwork.room

import com.example.archaeologyfieldwork.models.MainModel
import androidx.room.*

@Dao
interface HillfortDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hillfort: MainModel)

    @Query("SELECT * FROM MainModel")
    fun findAll(): List<MainModel>

    @Query("SELECT * FROM MainModel WHERE favourite == :favourite")
    fun findAllFavs(favourite: Boolean): List<MainModel>

    @Query("select * from MainModel where id = :id")
    fun findById(id: Long): MainModel

    @Update
    fun update(hillfort: MainModel)

    @Delete
    fun deleteHillfort(hillfort: MainModel)
}