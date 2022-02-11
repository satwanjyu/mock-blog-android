package com.satwanjyu.blog_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satwanjyu.blog_android.data.Post
import com.satwanjyu.blog_android.ui.theme.BlogandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val blogViewModel: BlogViewModel by viewModels()

    private val uiState = mutableStateOf<PostsUiState>(PostsUiState.Success(emptyList(), { }))

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
                    App(uiState = uiState.value)
                }
            }
        }
    }
}

@Composable
fun App(uiState: PostsUiState) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainscreen") {
        composable("mainscreen") {
            MainScreen(navController = navController, uiState = uiState)
        }
        composable("newpost") {
            val success = uiState as PostsUiState.Success
            ComposePost(navController, success.sendDraft)
        }
    }
}

@Composable
fun MainScreen(navController: NavController, uiState: PostsUiState) {
    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { navController.navigate("newpost") }) {
            Text(text = "Compose")
        }
        when (uiState) {
            is PostsUiState.Success ->
                PostList(posts = uiState.posts)
        }
    }
}

@Composable
fun PostList(posts: List<Post>) {
    LazyColumn {
        items(posts) { post ->
            Text(text = post.id, modifier = Modifier.padding())
            Text(text = post.content, modifier = Modifier.padding())
            Divider()
        }
    }
}

@Composable
fun ComposePost(navController: NavController, send: (String) -> Unit) {
    var draft by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(value = draft, onValueChange = { draft = it })
        Button(onClick = {
            send(draft)
            navController.navigate("mainscreen")
        }) {
            Text(text = "Send")
        }
    }
}
