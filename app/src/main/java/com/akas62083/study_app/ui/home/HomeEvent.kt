package com.akas62083.study_app.ui.home

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType

sealed interface HomeEvent {
    data object CloseAllButtonClicked: HomeEvent
    data class OnFolderClicked(val folder: FolderEntity): HomeEvent

    //---
    data object AddFolderButtonClicked: HomeEvent
    data class AddTextButtonClicked(val folder: FolderEntity): HomeEvent
    data class DeleteFolderButtonClicked(val folder: FolderEntity): HomeEvent
    data class DeleteTextButtonClicked(val text: TextEntity): HomeEvent
    data class RenameFolderName(val folder: FolderEntity): HomeEvent
    //---

    sealed interface AddFolderDialog: HomeEvent {
        data class NameChanged(val value: String) : AddFolderDialog
        data object IconClicked: AddFolderDialog
        data object ConfirmClicked : AddFolderDialog
        data object DismissClicked : AddFolderDialog
    }
    sealed interface AddTextDialog: HomeEvent {
        data class ChangeTextType(val textType: TextType): AddTextDialog
        data class NameChanged(val value: String): AddTextDialog
        data object ConfirmClicked: AddTextDialog
        data object DismissClicked: AddTextDialog
    }
    sealed interface DeleteFolderDialog: HomeEvent {
        data object ConfirmClicked: DeleteFolderDialog
        data object DismissClicked: DeleteFolderDialog
    }
    sealed interface DeleteTextDialog: HomeEvent {
        data object ConfirmClicked: DeleteTextDialog
        data object DismissClicked: DeleteTextDialog
    }
    sealed interface RenameFolderDialog: HomeEvent {
        data object ConfirmClicked: RenameFolderDialog
        data object DismissClicked: RenameFolderDialog
        data class NameChanged(val value: String): RenameFolderDialog
        data object IconClicked: RenameFolderDialog
    }
}