package com.example.mvi.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvi.api.RetrofitBuilder
import com.example.mvi.ui.main.state.MainViewState
import com.example.mvi.util.ApiEmptyResponse
import com.example.mvi.util.ApiErrorResponse
import com.example.mvi.util.ApiSuccessResponse
import com.example.mvi.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getBlogPost()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        blogPosts = apiResponse.body
                                    )

                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                ) // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )// Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitBuilder.apiService.getUser(userId)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(
                                        user = apiResponse.body
                                    )

                                )
                            }
                            is ApiErrorResponse -> {
                                value = DataState.error(
                                    message = apiResponse.errorMessage
                                ) // Handle Error?
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(
                                    message = "HTTP 204. Returned NOTHING!"
                                )// Handle Empty? (error)
                            }
                        }
                    }
                }
            }
    }

}