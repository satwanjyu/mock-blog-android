package com.satwanjyu.blog

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satwanjyu.blog.posts.ComposePost
import com.satwanjyu.blog.posts.Posts
import com.satwanjyu.blog.posts.ui.PostsUiState

@Composable
fun BlogScaffold(
    uiState: PostsUiState,
    draft: String,
    updateDraft: (String) -> Unit,
    sendDraft: (String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainscreen") {
        composable("mainscreen") {
            Posts(navController = navController, uiState = uiState)
        }
        composable("newpost") {
            ComposePost(navController, draft, updateDraft, sendDraft)
        }
    }
}