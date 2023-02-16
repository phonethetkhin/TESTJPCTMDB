package com.ptk.testjpctmdb.data.network

import com.ptk.testjpctmdb.data.dto.MovieDetailResponseModel
import com.ptk.testjpctmdb.data.dto.MovieResponseModel


interface ApiService {

    suspend fun getPopularMovies(): MovieResponseModel
    suspend fun getUpcomingMovies(): MovieResponseModel
    suspend fun getDetail(movieId:Int): MovieDetailResponseModel


}