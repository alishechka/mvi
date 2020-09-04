package com.example.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvi.api.RetrofitBuilder
import com.example.mvi.ui.main.state.MainViewState
import com.example.mvi.util.ApiEmptyResponse
import com.example.mvi.util.ApiErrorResponse
import com.example.mvi.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts(): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPost()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    blogPosts = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }


    fun getUser(userId: String): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    user = apiResponse.body
                                )
                            }
                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }

}