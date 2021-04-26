package com.example.marketing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Offer::class, Users::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val groupsDAO: AppDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `Users` (`id` INTEGER DEFAULT 0 NOT NULL, `phone` TEXT NOT NULL, `password` TEXT NOT NULL,  `role` TEXT NOT NULL, PRIMARY KEY(`id`))")
            }
        }
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "groupsDatabase"
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                }
                return instance
            }
        }
    }

}