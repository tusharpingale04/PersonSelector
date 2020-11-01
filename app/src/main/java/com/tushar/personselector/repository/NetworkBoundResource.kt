package com.tushar.personselector.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.tushar.personselector.AppController
import com.tushar.personselector.R
import com.tushar.personselector.model.NetworkResponseModel
import com.tushar.personselector.network.ApiError
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * This Class helps to fetch the data from network and persist into DB and then
 * send it back to UI.
 */
abstract class NetworkBoundResource<ResultType,RequestType : NetworkResponseModel> {

    fun asFlow() = flow {
        emit(Resource.loading(null))
        val dbValue = loadFromDb().first()
        if (shouldFetch(dbValue)) {
            emit(Resource.loading(dbValue))
            try {
                val apiResponse = fetchFromNetwork()
                if(apiResponse.isSuccessful && apiResponse.body() != null && apiResponse.code() == 200){

                    saveNetworkResult(processResponse(apiResponse)!!)
                    emitAll(loadFromDb().map { Resource.success(it) })
                }else{
                    val apiError: ApiError = Gson().fromJson(apiResponse.errorBody()?.charStream(), ApiError::class.java)
                    val errorMsg = if (!apiError.errorMessage.isNullOrEmpty()) {
                        apiError.errorMessage
                    } else{
                        AppController.getInstance().getString(R.string.error_server)
                    }
                    onFetchFailed(errorMsg)
                    emitAll(loadFromDb().map {
                        Resource.error(errorMsg, it)
                    })
                }
            }catch (e: Exception){
                // Exception occurred! Emit error
                emit(Resource.error(getErrorMessage(e), null))
                e.printStackTrace()
            }
        } else {
            emitAll(loadFromDb().map { Resource.success(it) })
        }
    }

    /** 
     * @param throwable class to check instance of when exception occurred 
     */
    private fun getErrorMessage(throwable: Throwable): String {
        return when {
            throwable is SocketTimeoutException -> {
                AppController.getInstance().getString(R.string.error_network_timeout)
            }
            throwable.fillInStackTrace() is UnknownHostException -> {
                AppController.getInstance().getString(R.string.error_no_connection)
            }
            else -> {
                AppController.getInstance().getString(R.string.error_server)
            }
        }
    }

    @WorkerThread
    protected open fun processResponse(response: Response<RequestType>) = response.body()

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(resp: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Response<RequestType>

    @MainThread
    protected abstract fun onFetchFailed(message: String?)
}
