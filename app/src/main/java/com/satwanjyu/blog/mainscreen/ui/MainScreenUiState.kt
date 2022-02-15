package com.satwanjyu.blog.mainscreen.ui

import com.satwanjyu.blog.mainscreen.data.Post

sealed class MainScreenUiState {
    abstract val posts: List<Post>

    data class Loading(
        override val posts: List<Post> = emptyList()
    ) : MainScreenUiState()

    data class Success(
        override val posts: List<Post> = emptyList()
    ) : MainScreenUiState()

    data class Error(
        override val posts: List<Post> = emptyList(),
        val message: String
    ) : MainScreenUiState()
}

