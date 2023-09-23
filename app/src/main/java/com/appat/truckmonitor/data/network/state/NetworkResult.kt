package com.appat.truckmonitor.data.network.state

import androidx.annotation.StringRes

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val errorCode: Int = 0
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, errorCode: Int, data: T? = null) : NetworkResult<T>(data, message, errorCode)
    class Loading<T>(@StringRes message: Int) : NetworkResult<T>()
}