package com.satwanjyu.blog.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satwanjyu.blog.mainscreen.data.Post
import com.satwanjyu.blog.mainscreen.ui.MainScreenUiState

@Composable
fun MainScreen(navController: NavController, uiState: MainScreenUiState) {
    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { navController.navigate("newpost") }) {
            Text(text = "Compose")
        }
        when (uiState) {
            is MainScreenUiState.Loading ->
                Box {
                    PostList(posts = uiState.posts)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopCenter)
                    )
                }

            is MainScreenUiState.Success ->
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
fun ComposePost(
    navController: NavController,
    draft: String,
    updateDraft: (String) -> Unit,
    send: (String) -> Unit
) {

    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(value = draft, onValueChange = { updateDraft(it) })
        Button(onClick = {
            send(draft)
            navController.navigate("mainscreen")
        }) {
            Text(text = "Send")
        }
    }
}