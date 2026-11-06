package com.akas62083.study_app.ui.home.component

import android.R.attr.maxLines
import android.R.attr.text
import android.system.Os.rename
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akas62083.study_app.R
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.ui.home.HomeEvent
import com.akas62083.study_app.ui.home.HomeUiState
import java.nio.file.Files.delete

@Composable
fun ContentList(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    addTextButtonClicked: (FolderEntity) -> Unit,
    deleteFolderButtonClicked: (FolderEntity) -> Unit,
    deleteTextButtonClicked: (TextEntity) -> Unit,
    renameFolderButtonClicked: (FolderEntity) -> Unit,
    onFolderClicked: (FolderEntity) -> Unit,
    textClicked: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(uiState.folderWithTexts) { folderWithTexts ->
            FolderItem(
                folder = folderWithTexts.folder,
                uiState = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable(
                        interactionSource = null,
                        indication = null
                    ){ onFolderClicked(folderWithTexts.folder) },
                delete = { deleteFolderButtonClicked(it) },
                rename = { renameFolderButtonClicked(it) },
                addText = {
                    if(!folderWithTexts.folder.isOpened) {
                        onFolderClicked(folderWithTexts.folder)
                    }
                    addTextButtonClicked(it)
                }
            )
            AnimatedContent(
                targetState = folderWithTexts.folder.isOpened,

            ) { target ->
                if(target) {
                    Column {
                        folderWithTexts.texts.forEach { text ->
                            TextItem(
                                text = text,
                                uiState = uiState,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable(
                                        indication = null,
                                        interactionSource = null
                                    ){ textClicked(text.id) },
                                delete = { deleteTextButtonClicked(text) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FolderItem(
    folder: FolderEntity,
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    delete: (FolderEntity) -> Unit,
    rename: (FolderEntity) -> Unit,
    addText: (FolderEntity) -> Unit
) {
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    val iconDegree by animateFloatAsState(
        if(!folder.isOpened) {
            0f
        } else {
            90f
        }
    )
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = if(folder.isRed) Color.Red else Color.Black,
            modifier = Modifier.rotate(iconDegree)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = folder.name,
            style = MaterialTheme.typography.bodyMedium.copy(color = if(folder.isRed) Color.Red else Color.Black),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = if(folder.isRed) Color.Red else Color.Black,
            modifier = Modifier.clickable {
                addText(folder)
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = if(folder.isRed) Color.Red else Color.Black,
                modifier = Modifier.clickable {
                    dropDownMenuExpanded = !dropDownMenuExpanded
                }
            )
            DropdownMenu(
                expanded = dropDownMenuExpanded,
                onDismissRequest = { dropDownMenuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.rename),
                            style = MaterialTheme.typography.labelLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    onClick = { dropDownMenuExpanded = false; rename(folder) }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.delete),
                            style = MaterialTheme.typography.labelLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    onClick = { dropDownMenuExpanded = false; delete(folder) }
                )
            }
        }
    }
}

@Composable
private fun TextItem(
    text: TextEntity,
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    delete: (TextEntity) -> Unit,
) {
    Row(modifier = modifier) {
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = when(text.textType) {
                TextType.TEXT -> Icons.Default.TextSnippet
                TextType.BLANK -> Icons.Default.MenuBook
                TextType.CARD -> Icons.Default.Style
            },
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text.name,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = Modifier.clickable {
                delete(text)
            }
        )
    }
}