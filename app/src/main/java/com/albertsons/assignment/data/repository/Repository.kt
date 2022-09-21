package com.albertsons.assignment.data.repository

import com.albertsons.assignment.data.datasource.RemoteDataSource
import com.albertsons.assignment.data.network.Api
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api) : RemoteDataSource() {

    suspend fun getMeanings(shortForm: String) = getResult {
        api.getMeanings(shortForm)
    }
}