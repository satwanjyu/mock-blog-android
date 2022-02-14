package com.satwanjyu.blog_android

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwanjyu.blog_android.data.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PostRepository {
    val posts: Flow<List<Post>>
    suspend fun updatePosts()
    suspend fun insertPost(content: String)
}

sealed class PostsUiState {
    data class Success(
        val posts: List<Post> = emptyList(),
    ) : PostsUiState()
}

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    val draft = mutableStateOf("")

    private val _uiState = MutableStateFlow(PostsUiState.Success())
    val uiState: StateFlow<PostsUiState> = _uiState

    init {
        viewModelScope.launch {
            postRepository.posts.collect { posts ->
                _uiState.value = PostsUiState.Success(
                    posts,
                )
            }
        }
        viewModelScope.launch {
            postRepository.updatePosts()
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