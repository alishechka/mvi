package com.example.mvi.api

import com.example.mvi.model.BlogPost
import com.example.mvi.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

    @GET("placeholder/blogs")
    fun getBlogPost(): List<BlogPost>
}