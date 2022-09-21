package com.albertsons.assignment.data.network

data class Result<out T>(val status: Status, val data: T? = null, val message: String? = null) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(msg: String, data: T? = null): Result<T> {
            return Result(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }
}