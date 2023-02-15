package com.ptk.testjpctmdb.data.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MovieResponseModel(
    @SerialName("page")
    val page: Int? = null,

    @SerialName("results")
    val results: List<MovieModel>? = null,

    @SerialName("total_pages")
    val total_pages: Int? = null,

    @SerialName("total_results")
    val total_results: Int? = null,
)

@kotlinx.serialization.Serializable
data class MovieModel(

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    var isFav: Boolean = false
)
