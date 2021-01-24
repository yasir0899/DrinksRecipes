package com.example.drinksrecipes.modules.home.repo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.drinksrecipes.models.responseModel.GetDrinksResponseModel
import com.example.drinksrecipes.modules.room.DrinksDatabase
import com.example.drinksrecipes.modules.room.DrinksTableModel
import com.example.drinksrecipes.restAPI.ErrorDto
import com.example.drinksrecipes.restAPI.RestApiClient
import com.example.drinksrecipes.restAPI.RetrofitApiManager
import com.example.drinksrecipes.utils.AppController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DrinksRepository {
    var drinkDatabase: DrinksDatabase? = null
    val data = MutableLiveData<Any>()
    var drinksTableModel = MutableLiveData<ArrayList<DrinksTableModel>>()

    fun callApi(map: HashMap<String, String>): LiveData<Any> {
        object : RetrofitApiManager<GetDrinksResponseModel>(AppController.ApplicationContext) {

            override fun onSuccess(t: GetDrinksResponseModel?) {

                data.value = t

            }

            init {
                callServer(
                    RestApiClient.getClient(addHeaders = false).getDrinks(map)
                )
            }

            override fun onFailure(t: ErrorDto?) {

                data.value = t
            }


            override fun tokenRefreshed() {


            }


        }

        return data
    }


    private fun initializeDB(context: Context): DrinksDatabase {
        return DrinksDatabase.getDataseClient(context)
    }

    fun insertData(
        context: Context,
        id:String,
        title: String?,
        desc: String?,
        isAlcoholic: String?,
        img: String?,
    ) {

        drinkDatabase = initializeDB(context)

        CoroutineScope(IO).launch {
            val drinksTableModel = DrinksTableModel(id,title, desc, isAlcoholic, img)
            drinkDatabase!!.drinksDao().insertData(drinksTableModel)
        }

    }

    fun getDrinksFromRoom(context: Context): LiveData<ArrayList<DrinksTableModel>> {
        drinkDatabase = initializeDB(context)
        CoroutineScope(IO).launch {
            drinksTableModel.postValue(ArrayList(drinkDatabase!!.drinksDao().getAll()))
        }
        return drinksTableModel
    }
}