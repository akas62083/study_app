package com.akas62083.study_app.ui.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
}