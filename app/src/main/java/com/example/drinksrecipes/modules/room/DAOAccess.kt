package com.example.drinksrecipes.modules.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(drinksTableModel: DrinksTableModel)

    @Query("SELECT * FROM drinks")
    suspend fun getAll(): List<DrinksTableModel>
}