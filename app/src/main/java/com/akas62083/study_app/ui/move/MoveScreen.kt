package com.akas62083.study_app.ui.move

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akas62083.study_app.ui.detail.DetailViewModel

@Composable
fun MoveScreen(
    viewModel: MoveViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
}
