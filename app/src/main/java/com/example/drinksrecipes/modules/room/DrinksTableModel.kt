package com.example.drinksrecipes.modules.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Drinks")
data class DrinksTableModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "desc")
    var desc: String?,
    @ColumnInfo(name = "isAlcoholic")
    var isAlcoholic: String?,
    @ColumnInfo(name = "img")
    var img: String?

)
