package com.ptk.testjpctmdb.ui.ui_state

import com.ptk.testjpctmdb.data.dto.MovieModel

data class HomeUIStates(
    val searchFieldValue: String = "",
    val currentPage: Int = 0,
    val showLoadingDialog: Boolean = false,
    val recommendedMovies: List<MovieModel>? = null,
    val upcomingMovies: List<MovieModel>? = null
)