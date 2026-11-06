package com.akas62083.study_app.ui.home

import android.R.attr.data
import android.R.attr.value
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.ui.detail.DetailUiState
import com.akas62083.study_app.usecase.DeleteFolderUseCase
import com.akas62083.study_app.usecase.DeleteTextUseCase
import com.akas62083.study_app.usecase.GetAllFolderWithTextsUseCase
import com.akas62083.study_app.usecase.InsertFolderUseCase
import com.akas62083.study_app.usecase.InsertTextUseCase
import com.akas62083.study_app.usecase.RenameFolderUseCase
import com.akas62083.study_app.usecase.ToggleFolderIsOpenedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllFolderWithTextsUseCase: GetAllFolderWithTextsUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val insertTextUseCase: InsertTextUseCase,
    private val deleteTextUseCase: DeleteTextUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val renameFolderUseCase: RenameFolderUseCase,
    private val toggleFolderIsOpenedUseCase: ToggleFolderIsOpenedUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            getAllFolderWithTextsUseCase().collect {
                _uiState.update { currentState ->
                    currentState.copy(folderWithTexts = it)
                }
            }
        }
    }
    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.CloseAllButtonClicked -> { closeAllFolder() }
            is HomeEvent.OnFolderClicked -> { toggleFolderIsOpened(event.folder) }

            is HomeEvent.AddFolderButtonClicked -> { toggleAddFolderDialog() }
            is HomeEvent.AddTextButtonClicked -> { toggleAddTextDialog(event.folder) }
            is HomeEvent.DeleteFolderButtonClicked -> { toggleDeleteFolderDialog(event.folder) }
            is HomeEvent.DeleteTextButtonClicked -> { toggleDeleteTextDialog(event.text) }
            is HomeEvent.RenameFolderName -> { toggleRenameFolderDialog(event.folder) }

            is HomeEvent.AddFolderDialog.NameChanged -> { onValueChange(event.value) }
            is HomeEvent.AddFolderDialog.IconClicked -> { changeFolderIsRed() }
            is HomeEvent.AddFolderDialog.ConfirmClicked -> { insertFolder(); toggleAddFolderDialog() }
            is HomeEvent.AddFolderDialog.DismissClicked -> { toggleAddFolderDialog() }

            is HomeEvent.AddTextDialog.ChangeTextType -> { changeTextType(event.textType) }
            is HomeEvent.AddTextDialog.NameChanged -> { onValueChange(event.value) }
            is HomeEvent.AddTextDialog.ConfirmClicked -> { insertText(); toggleAddTextDialog(null) }
            is HomeEvent.AddTextDialog.DismissClicked -> { toggleAddTextDialog(null) }

            is HomeEvent.DeleteFolderDialog.DismissClicked -> { toggleDeleteFolderDialog(null) }
            is HomeEvent.DeleteFolderDialog.ConfirmClicked -> { deleteFolder(); toggleDeleteFolderDialog(null) }
            is HomeEvent.DeleteTextDialog.DismissClicked -> { toggleDeleteTextDialog(null) }
            is HomeEvent.DeleteTextDialog.ConfirmClicked -> { deleteText(); toggleDeleteTextDialog(null) }

            is HomeEvent.RenameFolderDialog.NameChanged -> { onValueChange(event.value) }
            is HomeEvent.RenameFolderDialog.IconClicked -> { changeFolderIsRed() }
            is HomeEvent.RenameFolderDialog.ConfirmClicked -> { renameFolder(); toggleRenameFolderDialog(null) }
            is HomeEvent.RenameFolderDialog.DismissClicked -> { toggleRenameFolderDialog(null) }
        }
    }

    private fun toggleAddFolderDialog() { _uiState.update { currentState -> currentState.copy(dialogState = if(currentState.dialogState is HomeDialogState.AddFolder) HomeDialogState.None else HomeDialogState.AddFolder()) } }
    private fun toggleAddTextDialog(folder: FolderEntity?) { _uiState.update { currentState -> currentState.copy(dialogState = if(currentState.dialogState is HomeDialogState.AddText) HomeDialogState.None else HomeDialogState.AddText(parentFolder = folder!!)) } }
    private fun toggleDeleteFolderDialog(folder: FolderEntity?) { _uiState.update { currentState -> currentState.copy(dialogState = if(currentState.dialogState is HomeDialogState.DeleteFolder) HomeDialogState.None else HomeDialogState.DeleteFolder(folder!!)) } }
    private fun toggleDeleteTextDialog(text: TextEntity?) { _uiState.update { currentState -> currentState.copy(dialogState = if(currentState.dialogState is HomeDialogState.DeleteText) HomeDialogState.None else HomeDialogState.DeleteText(text!!)) } }
    private fun toggleRenameFolderDialog(folder: FolderEntity?) { _uiState.update { currentState -> currentState.copy( dialogState = if(currentState.dialogState is HomeDialogState.RenameFolder) HomeDialogState.None else HomeDialogState.RenameFolder(folder = folder!!, folderName = folder.name, isRed = folder.isRed)) } }

    private fun onValueChange(value: String) {
        when(uiState.value.dialogState) {
            is HomeDialogState.AddFolder -> {
                val state = uiState.value.dialogState as HomeDialogState.AddFolder
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = state.copy(folderName = value)
                    )
                }
            }
            is HomeDialogState.AddText -> {
                val state = uiState.value.dialogState as HomeDialogState.AddText
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = state.copy(textName = value)
                    )
                }
            }
            is HomeDialogState.RenameFolder -> {
                val state = uiState.value.dialogState as HomeDialogState.RenameFolder
                _uiState.update { currentState ->
                    currentState.copy(
                        dialogState = state.copy(folderName = value)
                    )
                }
            }
            else -> {}
        }
    }
    private fun changeFolderIsRed() {
        when(uiState.value.dialogState) {
            is HomeDialogState.AddFolder -> {
                val dialogState = uiState.value.dialogState as HomeDialogState.AddFolder
                _uiState.update { currentState ->
                    currentState.copy(dialogState = dialogState.copy(isRed = !dialogState.isRed))
                }
            }
            is HomeDialogState.RenameFolder -> {
                val dialogState = uiState.value.dialogState as HomeDialogState.RenameFolder
                _uiState.update { currentState ->
                    currentState.copy(dialogState = dialogState.copy(isRed = !dialogState.isRed))
                }
            }
            else -> {}
        }
    }
    private fun insertFolder() {
        val dialogState = uiState.value.dialogState as HomeDialogState.AddFolder
        viewModelScope.launch {
            insertFolderUseCase(
                name = dialogState.folderName,
                isRed = dialogState.isRed
            )
        }
    }
    private fun insertText() {
        val dialogState = uiState.value.dialogState as HomeDialogState.AddText
        viewModelScope.launch {
            insertTextUseCase(
                name = dialogState.textName,
                textType = dialogState.textType,
                folderId = dialogState.parentFolder.id
            )
        }
    }
    private fun deleteFolder() {
        val dialogState = uiState.value.dialogState as HomeDialogState.DeleteFolder
        viewModelScope.launch {
            deleteFolderUseCase(dialogState.folder)
        }
    }
    private fun deleteText() {
        val dialogState = uiState.value.dialogState as HomeDialogState.DeleteText
        viewModelScope.launch {
           deleteTextUseCase(dialogState.text)
        }
    }
    private fun renameFolder() {
        val dialogState = uiState.value.dialogState as HomeDialogState.RenameFolder
        viewModelScope.launch {
            renameFolderUseCase(
                name = dialogState.folderName,
                isRed = dialogState.isRed,
                folder = dialogState.folder
            )
        }
    }
    private fun changeTextType(textType: TextType) {
        val dialogState = uiState.value.dialogState as HomeDialogState.AddText
        _uiState.update { currentState ->
            currentState.copy(dialogState = dialogState.copy(textType = textType))
        }
    }
    private fun toggleFolderIsOpened(folder: FolderEntity) {
        viewModelScope.launch {
            toggleFolderIsOpenedUseCase(folder)
        }
    }
    private fun closeAllFolder() {

    }
}