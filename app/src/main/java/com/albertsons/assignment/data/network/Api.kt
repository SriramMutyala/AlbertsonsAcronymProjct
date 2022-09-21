package com.albertsons.assignment.data.network

import com.albertsons.assignment.data.model.Dictionary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(EndPoints.DICTIONARY)
    suspend fun getMeanings(
        @Query("sf") shortForm: String
    ): Response<List<Dictionary>>
}