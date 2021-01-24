package com.example.drinksrecipes.modules.home.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.drinksrecipes.modules.home.repo.DrinksRepository
import com.example.drinksrecipes.modules.room.DrinksTableModel

class DrinksVM : ViewModel() {


    fun getDrinks(map: HashMap<String, String>): LiveData<Any> {
        return DrinksRepository().callApi(map)
    }


    fun insertData(
        context: Context,
        id:String,
        title: String?,
        desc: String?,
        isAlcoholic: String?,
        img: String?
    ) {
        return DrinksRepository().insertData(context,id, title, desc, isAlcoholic, img)
    }

    fun getDrinksFromRoom(context: Context): LiveData<ArrayList<DrinksTableModel>> {
        return DrinksRepository().getDrinksFromRoom(context)
    }
}