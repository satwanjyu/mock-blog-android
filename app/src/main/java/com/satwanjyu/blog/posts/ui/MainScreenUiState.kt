package com.satwanjyu.blog.posts.ui

import com.satwanjyu.blog.posts.data.Post

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

