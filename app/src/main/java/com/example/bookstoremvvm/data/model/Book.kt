package com.example.bookstoremvvm.data.model

import com.google.gson.annotations.SerializedName

data class Book (
    val id: Int?,
    val name: String?,
    val author: String?,
    val publisher: String?,
    val price: Double?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_best_seller") val bestSeller: Boolean
)