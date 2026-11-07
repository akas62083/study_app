package com.akas62083.study_app.ui.detail.component.content_component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.ui.detail.CheckBoxAction
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.RunScreenData
import com.akas62083.study_app.ui.detail.TabStatus
import com.akas62083.study_app.ui.detail.component.content_component.run_component.DetailRunBlankScreen
import com.akas62083.study_app.ui.detail.component.content_component.run_component.DetailRunCardScreen
import com.akas62083.study_app.ui.detail.component.content_component.component.DetailErrorScreen
import com.akas62083.study_app.ui.detail.component.content_component.run_component.DetailRunTextScreen

@Composable
fun DetailRunScreen(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    tabStatus: TabStatus.Run,
    checkboxClicked: (CheckBoxAction) -> Unit,
    startButtonClicked: () -> Unit
) {
    Column(modifier) {
            when(tabStatus.screenData) {
                is RunScreenData.Text -> {
                    DetailRunTextScreen(
                        uiState = uiState,
                        modifier = Modifier.fillMaxSize(),
                        tabStatus = tabStatus.screenData
                    )
                }
                is RunScreenData.Card -> {
                    DetailRunCardScreen(
                        uiState = uiState,
                        modifier = Modifier.fillMaxSize(),
                        tabStatus = tabStatus.screenData,
                        checkboxClicked = checkboxClicked,
                        startButtonClicked = startButtonClicked
                    )
                }
                is RunScreenData.Blank -> {
                    DetailRunBlankScreen(
                        uiState = uiState,
                        modifier = Modifier.fillMaxSize(),
                        tabStatus = tabStatus.screenData
                    )
                }
                is RunScreenData.Error -> {
                    DetailErrorScreen(
                        tabStatus = tabStatus
                    )
                }
            }
    }
}
