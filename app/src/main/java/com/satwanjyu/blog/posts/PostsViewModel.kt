package com.satwanjyu.blog.posts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.ui.PostsUiState
import com.satwanjyu.blog.shared.data.CachedLiveRepository
import com.satwanjyu.blog.shared.data.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: CachedLiveRepository<Post>
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
        postRepository.cachePosts()
    }

    private suspend fun getPosts() {
        postRepository.read().collect { data ->
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

    fun sendDraft() {
        viewModelScope.launch {
            postRepository.create(Post("0", draft.value))
        }
    }

    fun updateDraft(draft: String) {
        this.draft.value = draft
    }
}