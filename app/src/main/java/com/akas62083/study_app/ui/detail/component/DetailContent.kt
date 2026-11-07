package com.akas62083.study_app.ui.detail.component

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akas62083.study_app.ui.detail.CheckBoxAction
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.TabStatus
import com.akas62083.study_app.ui.detail.component.content_component.DetailEditScreen
import com.akas62083.study_app.ui.detail.component.content_component.DetailRunScreen
import com.akas62083.study_app.ui.detail.component.content_component.DetailSaveScreen
import com.akas62083.study_app.ui.detail.component.content_component.DetailTextScreen

@Composable
fun DetailContent(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    checkboxClicked: (CheckBoxAction) -> Unit,
    startButtonClicked: () -> Unit
) {
    AnimatedContent(
        targetState = uiState.screen.tabStatus,
        contentKey = { it::class },
        modifier = Modifier.fillMaxSize()
    ) { tabState ->
        when(tabState) {
            is TabStatus.Save -> {
                DetailSaveScreen(
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    tabStatus = tabState
                )
            }
            is TabStatus.Text -> {
                DetailTextScreen(
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    tabStatus = tabState
                )
            }
            is TabStatus.Edit -> {
                DetailEditScreen(
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    onValueChange = { onTextChange(it) },
                    tabStatus = tabState
                )
            }
            is TabStatus.Run -> {
                DetailRunScreen(
                    uiState = uiState,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    tabStatus = tabState,
                    checkboxClicked = checkboxClicked,
                    startButtonClicked = startButtonClicked
                )
            }

            TabStatus.UnLoaded -> {

            }
        }
    }
}