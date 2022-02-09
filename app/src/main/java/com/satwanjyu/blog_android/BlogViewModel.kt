package com.satwanjyu.blog_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.data.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState.Success(emptyList()))
    val uiState: StateFlow<PostsUiState> = _uiState

    init {
        viewModelScope.launch {
            postRepository.posts.collect {
                _uiState.value = PostsUiState.Success(it)
            }
        }
        viewModelScope.launch {
            postRepository.updatePosts()
        }
    }
}

sealed class PostsUiState {
    data class Success(val posts: List<Post>) : PostsUiState()
}