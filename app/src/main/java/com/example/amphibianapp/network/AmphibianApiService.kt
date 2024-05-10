package com.example.amphibianapp.network

import com.example.amphibianapp.data.AmphibianData
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

interface AmphibianRoutes {
    @GET("amphibians")
   suspend fun getAmphibiansData(): List<AmphibianData>
}
class AmphibianApiService {
    private val retrofit =
        Retrofit.Builder().baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())).build()
    private val retrofitService:AmphibianRoutes by  lazy {
        retrofit.create(AmphibianRoutes::class.java)
    }
   suspend fun getAmphibians(): List<AmphibianData> {
       return retrofitService.getAmphibiansData()
   }
}