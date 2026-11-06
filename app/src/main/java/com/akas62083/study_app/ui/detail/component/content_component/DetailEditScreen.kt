package com.akas62083.study_app.ui.detail.component.content_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.TabStatus

@Composable
fun DetailEditScreen(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    tabStatus: TabStatus.Edit
) {
    Column(modifier) {
        if(uiState.text != null) {
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = uiState.screen.text?.sentence ?: "",
                onValueChange = { onValueChange(it) }
            )
        } else {

        }
    }
}