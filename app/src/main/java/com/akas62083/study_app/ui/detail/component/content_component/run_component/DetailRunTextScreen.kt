package com.akas62083.study_app.ui.detail.component.content_component.run_component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akas62083.study_app.domain.BlankTextStruct
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.RunScreenData

@Composable
fun DetailRunTextScreen(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    tabStatus: RunScreenData.Text
) {
    val textStruct = uiState.screen.textStruct as TextStruct.Text
    Column(modifier) {
        BlankTextStruct.Text(text = textStruct.text)
    }
}