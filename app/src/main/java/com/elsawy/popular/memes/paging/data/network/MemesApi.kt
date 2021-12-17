package com.elsawy.popular.memes.paging.data.network

import com.elsawy.popular.memes.paging.data.model.Memes
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MemesApi {

   @GET("/get_memes")
   suspend fun getMemes(): Memes

   companion object {

      val BASE_URL = "https://api.imgflip.com"
      private var retrofit: Retrofit? = null
      fun getApiClient(): Retrofit {
         val gson = GsonBuilder()
            .setLenient()
            .create()

         val okHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()

         if (retrofit == null) {
            retrofit = Retrofit.Builder()
               .baseUrl(BASE_URL)
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .build()
         }

         return retrofit!!
      }

   }
}