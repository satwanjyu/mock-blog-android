package com.satwanjyu.blog.posts.ui

import com.satwanjyu.blog.posts.data.Post

sealed class PostsUiState {
    abstract val posts: List<Post>

    data class Loading(
        override val posts: List<Post> = emptyList()
    ) : PostsUiState()

    data class Success(
        override val posts: List<Post> = emptyList()
    ) : PostsUiState()

    data class Error(
        override val posts: List<Post> = emptyList(),
        val message: String
    ) : PostsUiState()
}

