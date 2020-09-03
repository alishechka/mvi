package com.example.mvi.api

import androidx.lifecycle.LiveData
import com.example.mvi.model.BlogPost
import com.example.mvi.model.User
import com.example.mvi.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPost(): LiveData<GenericApiResponse<List<BlogPost>>>
}