package com.example.marketing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Offer::class], version = 1)
abstract class AppDatabase : RoomDatabase()  {
    abstract val  groupsDAO : AppDao
    companion object { @Volatile
    private var INSTANCE:AppDatabase? = null
        fun getInstance(context: Context):AppDatabase{
            synchronized(this){
                var instance:AppDatabase? = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "groupsDatabase"
                    ).build()
                }
                return instance
            }
        }
    }

}