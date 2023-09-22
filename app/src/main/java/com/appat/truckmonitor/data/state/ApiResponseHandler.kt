package com.appat.truckmonitor.data.state

import androidx.annotation.StringRes
import com.appat.truckmonitor.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class ApiResponseHandler {
    suspend fun <T> safeApiCall(defaultDispatcher: CoroutineDispatcher,
                                apiCall: suspend () -> Response<T>): Flow<NetworkResult<out T>> = flow {
        try {
            emit(loading(R.string.loader_msg))
            val response = apiCall()
            emit(performValidation(response))
        } catch (e: Exception) {
            emit(error(e.message ?: e.toString(), -1, null))
        }
    }.flowOn(defaultDispatcher)

    private fun <T> error(errorMessage: String, code: Int, data: T?): NetworkResult<T> =
        NetworkResult.Error(errorMessage, errorCode = code, data = data)

    private fun <T> loading(@StringRes message: Int): NetworkResult<T> =
        NetworkResult.Loading(message)

    private fun <T> performValidation(response: Response<T>): NetworkResult<T>
    {
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            error(response.message() ?: "", response.code(), body)
        }
    }
}