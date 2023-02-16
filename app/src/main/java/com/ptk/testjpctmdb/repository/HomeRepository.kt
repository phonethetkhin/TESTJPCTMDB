package com.ptk.testjpctmdb.repository

import android.app.Application
import com.ptk.testjpctmdb.R
import com.ptk.testjpctmdb.data.RemoteResource
import com.ptk.testjpctmdb.data.network.ApiService
import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val application: Application,
//    private val dataStore: LoginDataStore,
) {
    fun getPopularMovie() = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getPopularMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpRequestTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

    fun getUpcomingMovie() = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getUpcomingMovies()
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpRequestTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }
    fun getMovieDetail(movieId:Int) = channelFlow {
        send(RemoteResource.Loading)
        try {
            val response =
                apiService.getDetail(movieId)
            send(RemoteResource.Success(response))
        } catch (e: Exception) {
            when (e) {
                is HttpRequestTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                is ConnectTimeoutException -> {
                    send(RemoteResource.Failure(errorMessage = application.getString(R.string.connection_error_message)))
                }
                else -> {
                    val errorMessage = "Something went wrong: ${e.localizedMessage}"
                    send(RemoteResource.Failure(errorMessage = errorMessage))
                }
            }
        }
    }

}
