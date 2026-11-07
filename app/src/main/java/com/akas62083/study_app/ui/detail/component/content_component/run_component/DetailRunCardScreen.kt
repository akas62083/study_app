package com.akas62083.study_app.ui.detail.component.content_component.run_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.ui.detail.CardStatus
import com.akas62083.study_app.ui.detail.CheckBoxAction
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.RunScreenData
import com.akas62083.study_app.ui.detail.TabStatus
import com.akas62083.study_app.ui.detail.component.content_component.run_component.card_component.DetailRunFirstCard
import com.akas62083.study_app.ui.detail.component.content_component.run_component.card_component.DetailRunResultCard
import com.akas62083.study_app.ui.detail.component.content_component.run_component.card_component.DetailRunSolvingCard

@Composable
fun DetailRunCardScreen(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    tabStatus: RunScreenData.Card,
    checkboxClicked: (CheckBoxAction) -> Unit,
    startButtonClicked: () -> Unit
) {
    Column(modifier) {
        Surface(
            shadowElevation = 16.dp,
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            when (tabStatus.status) {
                CardStatus.FIRST -> {
                    DetailRunFirstCard(
                        modifier = Modifier.fillMaxSize(),
                        screenData = tabStatus,
                        checkboxClicked = checkboxClicked,
                        startButtonClicked = startButtonClicked
                    )
                }
                CardStatus.SOLVING -> {
                    DetailRunSolvingCard(
                        modifier = Modifier.fillMaxSize()
                    )
                }
                CardStatus.RESULT -> {
                    DetailRunResultCard(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
