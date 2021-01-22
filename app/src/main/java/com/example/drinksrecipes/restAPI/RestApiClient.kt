package com.example.drinksrecipes.restAPI



import android.util.Log
import com.example.drinksrecipes.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RestApiClient {
    companion object {


        private val retrofitWithOutHeaders: ApisList by lazy {
            val okHttpClientBuild = OkHttpClient().newBuilder()
            okHttpClientBuild.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor()).retryOnConnectionFailure(true)
            val okHttpClient = okHttpClientBuild.build()
            Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(okHttpClient).addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(ApisList::class.java)
        }



        fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor(HttpLogger())
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }


    }
}