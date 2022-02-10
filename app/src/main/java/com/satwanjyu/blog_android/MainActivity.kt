package com.satwanjyu.blog_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.ui.theme.BlogandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val blogViewModel: BlogViewModel by viewModels()

    private val uiState = mutableStateOf<PostsUiState>(PostsUiState.Success(emptyList()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                blogViewModel.uiState.collect {
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
                    when (uiState.value) {
                        is PostsUiState.Success -> PostList(posts = (uiState.value as PostsUiState.Success).posts)
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text(text = "Error: $message")
}

@Composable
fun PostList(posts: List<Post>) {
    Log.d("BlogMainActivity", "PostList recomposition")
    LazyColumn {
        items(posts) { post ->
            Text(text = post.id, modifier = Modifier.padding())
            Text(text = post.content, modifier = Modifier.padding())
            Divider()
        }
    }
}
