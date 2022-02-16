package com.satwanjyu.blog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.satwanjyu.blog.posts.PostsViewModel
import com.satwanjyu.blog.posts.ui.PostsUiState
import com.satwanjyu.blog.shared.ui.theme.BlogandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postsViewModel: PostsViewModel by viewModels()

    private val uiState = mutableStateOf<PostsUiState>(PostsUiState.Success())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postsViewModel.uiState.collect {
                    uiState.value = it
                }
            }
        }

        setContent {
            BlogandroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BlogScaffold(
                        uiState = uiState.value,
                        postsViewModel.draft.value,
                        { postsViewModel.updateDraft(it) },
                        { postsViewModel.sendDraft(it) }
                    )
                }
            }
        }
    }
}