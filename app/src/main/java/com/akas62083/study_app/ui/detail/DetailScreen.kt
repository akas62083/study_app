package com.akas62083.study_app.ui.detail

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.study_app.ui.detail.component.DetailBottomBar
import com.akas62083.study_app.ui.detail.component.DetailContent
import com.akas62083.study_app.ui.detail.component.DetailTopBar
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            DetailTopBar(
                uiState = uiState,
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 40.dp),
                tabClicked = { viewModel.onEvent(DetailEvent.TabClicked(it)) }
            )
        },
        bottomBar = {
            BottomAppBar {
                DetailBottomBar(
                    uiState = uiState
                )
            }
        }
    ) { innerParring ->
        Column(
            modifier = Modifier.padding(innerParring)
                .fillMaxSize()
        ) {
            DetailContent(
                uiState = uiState,
                modifier = Modifier.fillMaxSize(),
                onTextChange = { viewModel.onEvent(DetailEvent.ChangeText(it)) },
                checkboxClicked = { viewModel.onEvent(DetailEvent.CheckBoxClicked(it)) },
                startButtonClicked = { viewModel.onEvent(DetailEvent.StartButtonClicked) }
            )
        }
    }
}
