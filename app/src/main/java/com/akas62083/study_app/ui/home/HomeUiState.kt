package com.akas62083.study_app.ui.home

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType

data class HomeUiState (
    val folderWithTexts: List<FolderWithTexts> = emptyList(),
    val dialogState: HomeDialogState = HomeDialogState.None
)

sealed interface HomeDialogState {
    data object None: HomeDialogState
    data class AddFolder(
        val folderName: String = "",
        val isRed: Boolean = false,
    ): HomeDialogState {
        val enabled: Boolean = folderName != "" && folderName.length <= 10
    }
    data class AddText(
        val textName: String = "",
        val textType: TextType = TextType.TEXT,
        val parentFolder: FolderEntity
    ): HomeDialogState {
        val enabled: Boolean = textName != "" && textName.length <= 10
    }
    data class DeleteFolder(
        val folder: FolderEntity
    ): HomeDialogState
    data class DeleteText(
        val text: TextEntity
    ): HomeDialogState
    data class RenameFolder(
        val folder: FolderEntity,
        val folderName: String = "",
        val isRed: Boolean
    ): HomeDialogState {
        val enabled: Boolean = folderName != "" && folderName.length <= 10
    }
}