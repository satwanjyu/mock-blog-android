package com.satwanjyu.blog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satwanjyu.blog.mainscreen.data.Post
import com.satwanjyu.blog.mainscreen.ui.MainScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun updatePosts()
    suspend fun insertPost(content: String)
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    val draft = mutableStateOf("")

    private var posts: List<Post> = emptyList()

    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Success())
    val uiState: StateFlow<MainScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            getPosts()
        }
        viewModelScope.launch {
            updatePosts()
        }
    }

    private suspend fun getPosts() {
        postRepository.getPosts().collect {
            _uiState.value = MainScreenUiState.Success(it)
        }
    }

    private suspend fun updatePosts() {
        _uiState.value = MainScreenUiState.Loading(posts)
        postRepository.updatePosts()
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