package com.satwanjyu.blog

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satwanjyu.blog.mainscreen.ui.MainScreenUiState
import com.satwanjyu.blog.ui.mainscreen.ComposePost
import com.satwanjyu.blog.ui.mainscreen.MainScreen

@Composable
fun App(
    uiState: MainScreenUiState,
    draft: String,
    updateDraft: (String) -> Unit,
    sendDraft: (String) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "mainscreen") {
        composable("mainscreen") {
            MainScreen(navController = navController, uiState = uiState)
        }
        composable("newpost") {
            ComposePost(navController, draft, updateDraft, sendDraft)
        }
    }
}