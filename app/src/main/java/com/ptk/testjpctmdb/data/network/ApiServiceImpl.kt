package com.ptk.testjpctmdb.data.network

import com.ptk.testjpctmdb.data.dto.MovieDetailResponseModel
import com.ptk.testjpctmdb.data.dto.MovieResponseModel
import com.ptk.testjpctmdb.util.API_KEY
import com.ptk.testjpctmdb.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) : ApiService {

    override suspend fun getPopularMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getPopularMovies)
        contentType(ContentType.Application.Json)
        parameter("api_key", API_KEY)
    }

    override suspend fun getUpcomingMovies(): MovieResponseModel = client.get {
        url(BASE_URL + APIRoutes.getUpcomingMovies)
        contentType(ContentType.Application.Json)
        parameter("api_key", API_KEY)
    }

    override suspend fun getDetail(movieId: Int): MovieDetailResponseModel = client.get {
        url(BASE_URL + "movie/${movieId}")
        contentType(ContentType.Application.Json)
        parameter("api_key", API_KEY)
    }


}