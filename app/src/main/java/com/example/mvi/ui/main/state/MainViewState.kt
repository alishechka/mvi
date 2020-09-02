package com.example.mvi.ui.main.state

import com.example.mvi.model.BlogPost
import com.example.mvi.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)