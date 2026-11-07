package com.akas62083.study_app.ui.detail.component.content_component.run_component.card_component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.ui.detail.CheckBoxAction
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.RunScreenData
import com.akas62083.study_app.ui.detail.TabStatus

@Composable
fun DetailRunFirstCard(
    modifier: Modifier = Modifier,
    screenData: RunScreenData.Card,
    checkboxClicked: (CheckBoxAction) -> Unit,
    startButtonClicked: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
        ) {
            Text("出題範囲を選択")

            Text("ヘッダーを管理")
            Row {
                Checkbox(
                    checked = screenData.allHeaders,
                    onCheckedChange = { checkboxClicked(CheckBoxAction.AllHeaders) }
                )
                Text("全てのヘッダー")
            }
            screenData.headersKinds.forEach { header ->
                if(header != "") {
                    Row {
                        Checkbox(
                            checked = screenData.headers.contains(header) || screenData.allHeaders,
                            onCheckedChange = { checkboxClicked(CheckBoxAction.Header(header)) },
                            enabled = !screenData.allHeaders
                        )
                        Text(header)
                    }
                }
            }


            Text("タグを管理")
            Row {
                Checkbox(
                    checked = screenData.allTags,
                    onCheckedChange = { checkboxClicked(CheckBoxAction.AllTags) }
                )
                Text("全てのタグ")
            }
            screenData.tagsKinds.forEach { tag ->
                if(tag != "") {
                    Row {
                        Checkbox(
                            checked = screenData.tags.contains(tag) || screenData.allTags,
                            onCheckedChange = { checkboxClicked(CheckBoxAction.Tag(tag)) },
                            enabled = !screenData.allTags
                        )
                        Text(tag)
                    }
                }
            }

            Text("オプション")
            Row {
                Checkbox(
                    checked = screenData.shuffle,
                    onCheckedChange = { checkboxClicked(CheckBoxAction.Shuffle) }
                )
                Text("シャッフルする")
            }
            Row {
                Row {
                    Checkbox(
                        checked = screenData.omote,
                        onCheckedChange = { checkboxClicked(CheckBoxAction.Omote) }
                    )
                    Text("表カード")
                }
                Row {
                    Checkbox(
                        checked = screenData.ura,
                        onCheckedChange = { checkboxClicked(CheckBoxAction.Ura) }
                    )
                    Text("裏カード")
                }
            }

            Button(
                onClick = { startButtonClicked() },
                enabled = (screenData.allTags || screenData.tags.isNotEmpty()) && (screenData.allHeaders || screenData.headers.isNotEmpty()) && (screenData.ura || screenData.omote)
            ) { Text("開始") }
        }
    }
}
