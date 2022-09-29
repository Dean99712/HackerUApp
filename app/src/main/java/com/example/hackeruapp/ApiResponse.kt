package com.example.hackeruapp

import com.google.gson.annotations.SerializedName

data class ApiImage(@SerializedName("userImageURL") val  imageUrl: String)
data class ApiResponse(@SerializedName("hits") val  imagesList: List<ApiImage>)
