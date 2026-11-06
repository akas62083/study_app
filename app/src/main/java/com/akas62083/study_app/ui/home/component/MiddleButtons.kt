package com.akas62083.study_app.ui.home.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.akas62083.study_app.R
import com.akas62083.study_app.ui.home.HomeUiState

@Composable
fun MiddleButtons(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    closeAllButtonClicked: () -> Unit,
    addFolderButtonClicked: () -> Unit
) {
    Row(modifier = modifier) {
        Button(
            onClick = { closeAllButtonClicked() },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource( R.string.all_close_button_text),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = { addFolderButtonClicked() },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.add_folder_button_text),
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}