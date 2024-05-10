package com.example.amphibianapp.data

import com.example.amphibianapp.network.AmphibianRoutes

interface AmphibianRepository {
    suspend fun getData(): List<AmphibianData>
}

class NetworkAmphibianRepository(
   val amphibianAPI: AmphibianRoutes
) : AmphibianRepository{
    override suspend fun getData(): List<AmphibianData> = amphibianAPI.getAmphibiansData()
}