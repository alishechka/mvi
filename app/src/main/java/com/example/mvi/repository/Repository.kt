package com.example.mvi.repository

import androidx.lifecycle.LiveData
import com.example.mvi.api.RetrofitBuilder
import com.example.mvi.model.BlogPost
import com.example.mvi.model.User
import com.example.mvi.ui.main.state.MainViewState
import com.example.mvi.util.ApiSuccessResponse
import com.example.mvi.util.DataState
import com.example.mvi.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    data = MainViewState(
                        blogPosts = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitBuilder.apiService.getBlogPost()
            }
        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(
                        user = response.body
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.getUser(userId)
            }
        }.asLiveData()
    }
}