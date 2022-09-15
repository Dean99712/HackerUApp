package com.example.hackeruapp

import retrofit2.Retrofit
import retrofit2.create

interface ApiInterface {


    companion object {
        fun create(): ApiInterface {
            var retrofit = Retrofit
                .Builder()
                .build()
            return  retrofit.create(ApiInterface::class.java)
        }
    }
}