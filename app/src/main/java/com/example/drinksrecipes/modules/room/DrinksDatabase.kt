package com.example.drinksrecipes.modules.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DrinksTableModel::class], version = 1, exportSchema = false)
abstract class DrinksDatabase : RoomDatabase() {

    abstract fun drinksDao(): DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: DrinksDatabase? = null

        fun getDataseClient(context: Context): DrinksDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, DrinksDatabase::class.java, "DRINKS_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}