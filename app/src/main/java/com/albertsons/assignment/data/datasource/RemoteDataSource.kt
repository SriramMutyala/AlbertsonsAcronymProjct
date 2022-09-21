package com.albertsons.assignment.data.datasource

import com.albertsons.assignment.data.network.Result
import retrofit2.Response

abstract class RemoteDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.success(it)
                }
            }
            return Result.error(response.message())
        } catch (e: Exception) {
            return Result.error(e.message ?: e.toString())
        }
    }
}
