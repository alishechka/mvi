package com.example.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvi.model.BlogPost
import com.example.mvi.model.User
import com.example.mvi.repository.Repository
import com.example.mvi.ui.main.state.MainStateEvent
import com.example.mvi.ui.main.state.MainStateEvent.*
import com.example.mvi.ui.main.state.MainViewState
import com.example.mvi.util.AbsentLiveData
import com.example.mvi.util.DataState

class MainViewModel : ViewModel() {
    //event that happens
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    //which view you are presenting...data
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        println("DEBUG: New StateEvent detected: $stateEvent")
        when (stateEvent) {
            is GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }
            is GetUserEvent -> {
                return Repository.getUser(stateEvent.userId)
            }
            is None -> {
                return AbsentLiveData.create()
            }
        }
    }


    fun setBlogListData(blogPost: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPost
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()
        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}