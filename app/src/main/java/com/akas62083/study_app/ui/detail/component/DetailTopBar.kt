package com.akas62083.study_app.ui.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.akas62083.study_app.R
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.ui.detail.TabType

@Composable
fun DetailTopBar(
    uiState: DetailUiState,
    modifier: Modifier = Modifier,
    tabClicked: (TabType) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.icon_save),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.clickable {
                tabClicked(TabType.SAVE)
            }
        )
        Text(
            text = stringResource(R.string.icon_text),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.clickable {
                tabClicked(TabType.TEXT)
            }
        )
        Text(
            text = stringResource(R.string.icon_edit),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.clickable {
                tabClicked(TabType.EDIT)
            }
        )
        Text(
            text = stringResource(R.string.icon_run),
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.clickable {
                tabClicked(TabType.RUN)
            }
        )
    }
}