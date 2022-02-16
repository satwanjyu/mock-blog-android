package com.satwanjyu.blog.posts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satwanjyu.blog.posts.data.Post
import com.satwanjyu.blog.posts.ui.PostsUiState

@Composable
fun Posts(navController: NavController, uiState: PostsUiState) {
    Column(modifier = Modifier.padding(8.dp)) {
        Button(onClick = { navController.navigate("newpost") }) {
            Text(text = "Compose")
        }
        when (uiState) {
            is PostsUiState.Loading -> PostList(posts = uiState.posts, dataState = "Loading")
            is PostsUiState.Success -> PostList(posts = uiState.posts, dataState = "Success")
            is PostsUiState.Error -> PostList(posts = uiState.posts, dataState = uiState.message)
        }
    }
}

@Composable
fun PostList(posts: List<Post>, dataState: String) {
    Column {
        Text(text = dataState)
        LazyColumn {
            items(posts) { post ->
                Text(text = post.id, modifier = Modifier.padding())
                Text(text = post.content, modifier = Modifier.padding())
                Divider()
            }
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