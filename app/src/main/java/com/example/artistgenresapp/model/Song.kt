package com.example.artistgenresapp.model


import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
)