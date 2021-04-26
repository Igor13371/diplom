package com.example.marketing.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Query("SELECT * from offers")
    fun getAllOffers(): LiveData<List<Offer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(offer: Offer)

    @Query("DELETE FROM offers")
    suspend fun nukeTable()
}