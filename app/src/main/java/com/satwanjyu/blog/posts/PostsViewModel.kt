package com.satwanjyu.blog.posts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwanjyu.blog.posts.data.Data
import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.ui.PostsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    val draft = mutableStateOf("")

    private var posts: List<Post> = emptyList()

    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Success())
    val uiState: StateFlow<PostsUiState> = _uiState

    init {
        viewModelScope.launch {
            updatePosts()
        }
        viewModelScope.launch {
            getPosts()
        }
    }

    private suspend fun updatePosts() {
        _uiState.value = PostsUiState.Loading(posts)
        postRepository.updatePosts()
    }

    private suspend fun getPosts() {
        postRepository.getPosts().collect { data ->
            when (data) {
                is Data.Live ->
                    _uiState.value = PostsUiState.Success(data.data ?: emptyList())
                is Data.Loading ->
                    _uiState.value = PostsUiState.Loading(data.data ?: emptyList())
                is Data.Outdated ->
                    _uiState.value = PostsUiState.Error(
                        posts = data.data ?: emptyList(),
                        message = data.message
                    )
            }
        }
    }

    fun sendDraft(draft: String) {
        viewModelScope.launch {
            postRepository.insertPost(draft)
        }
    }

    fun updateDraft(draft: String) {
        this.draft.value = draft
    }
}