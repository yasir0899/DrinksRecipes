package com.example.drinksrecipes.restAPI

import com.example.drinksrecipes.models.responseModel.GetDrinksResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface ApisList {

    @GET("search.php?")
    fun getDrinks(
        @QueryMap s: HashMap<String, String>
    ): Call<GetDrinksResponseModel>


}
