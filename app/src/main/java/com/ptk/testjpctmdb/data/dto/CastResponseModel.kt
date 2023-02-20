package com.ptk.testjpctmdb.data.dto

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class CastResponseModel(

    @SerialName("cast")
    val cast: List<CastItem?>? = null,

    @SerialName("id")
    val id: Int? = null,
)

@kotlinx.serialization.Serializable
data class CastItem(

    @SerialName("cast_id")
    val castId: Int? = null,

    @SerialName("character")
    val character: String? = null,

    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("credit_id")
    val creditId: String? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("order")
    val order: Int? = null
)

