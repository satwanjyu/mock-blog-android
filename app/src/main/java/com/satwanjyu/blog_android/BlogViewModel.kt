package com.satwanjyu.blog_android

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
        val posts: List<Post>,
        val sendDraft: (String) -> Unit
    ) : PostsUiState()
}

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val postRepositoryImpl: PostRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState.Success(
        emptyList(),
        { sendDraft(it) }
    ))
    val uiState: StateFlow<PostsUiState> = _uiState

    init {
        viewModelScope.launch {
            postRepositoryImpl.posts.collect { posts ->
                _uiState.value = PostsUiState.Success(
                    posts,
                    { sendDraft(it) }
                )
            }
        }
        viewModelScope.launch {
            postRepositoryImpl.updatePosts()
        }
    }

    private fun sendDraft(draft: String) {
        viewModelScope.launch {
            postRepositoryImpl.insertPost(draft)
        }
    }
}