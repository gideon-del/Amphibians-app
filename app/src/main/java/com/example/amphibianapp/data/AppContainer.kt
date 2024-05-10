package com.example.amphibianapp.data

import com.example.amphibianapp.network.AmphibianRoutes
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
 val amphibianRepository: AmphibianRepository
}

class DefaultContainer():AppContainer {
 private val retrofit =
  Retrofit.Builder().baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
   .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).build()
 private val retrofitService: AmphibianRoutes by  lazy {
  retrofit.create(AmphibianRoutes::class.java)

 }
 override val amphibianRepository: AmphibianRepository by lazy {
  NetworkAmphibianRepository(retrofitService)
 }
}
