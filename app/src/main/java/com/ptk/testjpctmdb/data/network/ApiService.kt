package com.ptk.testjpctmdb.data.network

import com.ptk.testjpctmdb.data.dto.MovieResponseModel


interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel


}