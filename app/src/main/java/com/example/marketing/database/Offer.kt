package com.example.marketing.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class Offer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val url:String
)