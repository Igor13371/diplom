package com.example.marketing.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "choosenservice")
data class ChoosenService(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clientPhone:String,
    val email:String,
    val service:String
)
