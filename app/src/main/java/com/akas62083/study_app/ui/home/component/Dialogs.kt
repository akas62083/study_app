package com.akas62083.study_app.ui.home.component

import android.R.attr.name
import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion.Companion.Animated
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.akas62083.study_app.R
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.ui.home.HomeDialogState
import com.akas62083.study_app.ui.home.HomeUiState

@Composable
fun AddFolderDialog(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    cancelButtonClicked: () -> Unit,
    confirmButtonClicked: () -> Unit,
    onValueChange: (String) -> Unit,
    iconClicked: () -> Unit
) {
    val dialogState = uiState.dialogState as HomeDialogState.AddFolder
    val animatedColor = animateColorAsState(
        if(dialogState.isRed) Color.Red else Color.Black
    )
    val animatedDegree = animateFloatAsState(
        if(dialogState.isRed) 360f else 0f
    )
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { cancelButtonClicked() },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = animatedColor.value,
                            modifier = Modifier.rotate(animatedDegree.value)
                                .clickable(
                                    interactionSource = null,
                                    indication = null
                                ) { iconClicked() }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = dialogState.folderName,
                        onValueChange = { onValueChange(it) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = if (dialogState.enabled) Color.Black else Color.Red
                        ),
                        placeholder = { Text(
                            text = stringResource(R.string.create_new_folder),
                            color = Color.Gray
                        ) }
                    )
                }
                AnimatedContent(targetState = dialogState.enabled) {
                    if(!it) {
                        Text(
                            text = stringResource(R.string.sentence_one_to_ten),
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { confirmButtonClicked() },
                enabled = dialogState.enabled
            ) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelButtonClicked() }) {
                Text(
                    text = stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )
}

@Composable
fun AddTextDialog(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    cancelButtonClicked: () -> Unit,
    confirmButtonClicked: () -> Unit,
    onValueChange: (String) -> Unit,
    changeTextType: (TextType) -> Unit
) {
    val dialogState = uiState.dialogState as HomeDialogState.AddText
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { cancelButtonClicked() },
        text = {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = if(dialogState.parentFolder.isRed) Color.Red else Color.Black,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = dialogState.parentFolder.name,
                            style = MaterialTheme.typography.bodyMedium.copy(color = if(dialogState.parentFolder.isRed) Color.Red else Color.Black),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FilterChip(
                        onClick = { changeTextType(TextType.TEXT) },
                        label = {
                            Text(
                                text = stringResource(R.string.text),
                                style = MaterialTheme.typography.labelLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        },
                        selected = dialogState.textType == TextType.TEXT,
                    )
                    FilterChip(
                        onClick = { changeTextType(TextType.BLANK) },
                        label = {
                            Text(
                                text = stringResource(R.string.blank),
                                style = MaterialTheme.typography.labelLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        },
                        selected = dialogState.textType == TextType.BLANK,
                    )
                    FilterChip(
                        onClick = { changeTextType(TextType.CARD) },
                        label = {
                            Text(
                                text = stringResource(R.string.card),
                                style = MaterialTheme.typography.labelLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2
                            )
                        },
                        selected = dialogState.textType == TextType.CARD,
                    )

                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(IntrinsicSize.Min),
                    value = dialogState.textName,
                    onValueChange = { onValueChange(it) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = if (dialogState.enabled) Color.Black else Color.Red
                    ),
                    placeholder = { Text(
                        text = stringResource(R.string.create_new_text),
                        color = Color.Gray
                    ) }
                )
                AnimatedContent(targetState = dialogState.enabled) {
                    if(!it) {
                        Text(
                            text = stringResource(R.string.sentence_one_to_ten),
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { confirmButtonClicked() },
                enabled = dialogState.enabled
            ) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelButtonClicked() }) {
                Text(
                    text = stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )
}

@Composable
fun DeleteFolderDialog(
    uiState: HomeUiState,
    cancel: () -> Unit,
    confirmButtonClicked: () -> Unit
) {
    val dialogState = uiState.dialogState as HomeDialogState.DeleteFolder
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { cancel() },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.do_you_want_to_delete_this_folder),
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Red),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = dialogState.folder.name,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.this_action_cannot_be_undone_),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancel() }) {
                Text(
                    stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        confirmButton = {
            Button(onClick = { confirmButtonClicked() }) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )
}

@Composable
fun DeleteTextDialog(
    uiState: HomeUiState,
    cancel: () -> Unit,
    confirmButtonClicked: () -> Unit
) {
    val dialogState = uiState.dialogState as HomeDialogState.DeleteText
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { cancel() },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.do_you_want_to_delete_this_text),
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Red),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = dialogState.text.name,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.this_action_cannot_be_undone_),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Red),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancel() }) {
                Text(
                    stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        confirmButton = {
            Button(onClick = { confirmButtonClicked() }) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )
}

@Composable
fun RenameFolderDialog(
    uiState: HomeUiState,
    cancelButtonClicked: () -> Unit,
    iconClicked: () -> Unit,
    confirmButtonClicked: () -> Unit,
    onValueChange: (String) -> Unit
) {
    val dialogState = uiState.dialogState as HomeDialogState.RenameFolder
    val animatedColor = animateColorAsState(
        if(dialogState.isRed) Color.Red else Color.Black
    )
    val animatedDegree = animateFloatAsState(
        if(dialogState.isRed) 360f else 0f
    )
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = { cancelButtonClicked() },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .height(IntrinsicSize.Min)
                ) {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = animatedColor.value,
                            modifier = Modifier.rotate(animatedDegree.value)
                                .clickable(
                                    interactionSource = null,
                                    indication = null
                                ) { iconClicked() }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = dialogState.folderName,
                        onValueChange = { onValueChange(it) },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = if (dialogState.enabled) Color.Black else Color.Red
                        ),
                        placeholder = { Text(
                            text = stringResource(R.string.rename),
                            color = Color.Gray
                        ) }
                    )
                }
                AnimatedContent(targetState = dialogState.enabled) {
                    if(!it) {
                        Text(
                            text = stringResource(R.string.sentence_one_to_ten),
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Red)
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { confirmButtonClicked() },
                enabled = dialogState.enabled
            ) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { cancelButtonClicked() }) {
                Text(
                    text = stringResource(R.string.cancel_button),
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )
}