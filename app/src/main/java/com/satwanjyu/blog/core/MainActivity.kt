package com.satwanjyu.blog.core

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
import com.satwanjyu.blog.App
import com.satwanjyu.blog.MainScreenViewModel
import com.satwanjyu.blog.core.ui.theme.BlogandroidTheme
import com.satwanjyu.blog.mainscreen.ui.MainScreenUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainScreenViewModel: MainScreenViewModel by viewModels()

    private val uiState = mutableStateOf<MainScreenUiState>(MainScreenUiState.Success())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainScreenViewModel.uiState.collect {
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
                    App(
                        uiState = uiState.value,
                        mainScreenViewModel.draft.value,
                        { mainScreenViewModel.updateDraft(it) },
                        { mainScreenViewModel.sendDraft(it) }
                    )
                }
            }
        }
    }
}