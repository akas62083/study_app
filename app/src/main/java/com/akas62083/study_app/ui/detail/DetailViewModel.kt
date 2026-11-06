package com.akas62083.study_app.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.study_app.Route
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.usecase.ChangeTextUseCase
import com.akas62083.study_app.usecase.GetTextByIdUseCase
import com.akas62083.study_app.usecase.MakeTextStructUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTextByIdUseCase: GetTextByIdUseCase,
    private val changeTextUseCase: ChangeTextUseCase,
    private val makeTextStructUseCase: MakeTextStructUseCase
): ViewModel() {
    private val args = savedStateHandle.toRoute<Route.DETAIL>()
    private val _screenState = MutableStateFlow(DetailScreenState())


    val uiState: StateFlow<DetailUiState> =
        combine(
            _screenState,
            getTextByIdUseCase(args.id)
        ) { screenState, textEntity ->
            DetailUiState(
                screen = screenState.copy(text = screenState.text ?: textEntity),
                text = textEntity
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState()
        )

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.ChangeText -> { changeText(event.text) }
            is DetailEvent.TabClicked -> {
                saveText()
                _screenState.update { cs ->
                    cs.copy(textStruct = makeTextStructUseCase(uiState.value.screen.text ?: TextEntity(folderId = 0)))
                }
                tabClicked(event.tabType)
            }
            is DetailEvent.CheckBoxClicked -> {
                checkBoxClicked(event.checkBoxAction)
            }
            is DetailEvent.StartButtonClicked -> {
                startSolving()
            }
        }
    }

    private fun changeText(text: String) {
        _screenState.update { cs ->
            val baseText = cs.text ?: uiState.value.text
            cs.copy(text = baseText?.copy(sentence = text))
        }
    }
    private fun tabClicked(tabType: TabType) {
        _screenState.update {
            it.copy(
                tabStatus = when(tabType) {
                    TabType.SAVE -> { TabStatus.Save }
                    TabType.TEXT -> { TabStatus.Text }
                    TabType.EDIT -> { TabStatus.Edit }
                    TabType.RUN -> { TabStatus.Run(
                        screenData = when(uiState.value.screen.text?.textType ?: TextType.TEXT) {
                            TextType.TEXT -> { RunScreenData.Text }
                            TextType.BLANK -> { RunScreenData.Blank }
                            TextType.CARD -> { RunScreenData.Card(CardStatus.FIRST) }
                        }
                    ) }
                }
            )
        }
    }
    private fun saveText() {
        viewModelScope.launch {
            val editingText = _screenState.value.text
            if(editingText != null) {
                changeTextUseCase(editingText)
                _screenState.update { it.copy(text = null) }
            }
        }
    }
    private fun checkBoxClicked(checkBoxAction: CheckBoxAction) {
        val tabStatus = _screenState.value.tabStatus as TabStatus.Run
        val screenData = tabStatus.screenData as RunScreenData.Card
        when(checkBoxAction) {
            is CheckBoxAction.AllTags -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(allTags = !screenData.allTags)))
                }
            }
            is CheckBoxAction.Tag -> {
                if(screenData.tags.contains(checkBoxAction.tag)) {
                    _screenState.update { cs ->
                        cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(tags = screenData.tags - checkBoxAction.tag)))
                    }
                } else {
                    _screenState.update { cs ->
                        cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(tags = screenData.tags + checkBoxAction.tag)))
                    }
                }
            }
            is CheckBoxAction.AllHeaders -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(allHeaders = !screenData.allHeaders)))
                }
            }
            is CheckBoxAction.Header -> {
                if(screenData.headers.contains(checkBoxAction.header)) {
                    _screenState.update { cs ->
                        cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(headers = screenData.headers - checkBoxAction.header)))
                    }
                } else {
                    _screenState.update { cs ->
                        cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(headers = screenData.headers + checkBoxAction.header)))
                    }
                }
            }
            is CheckBoxAction.Shuffle -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(shuffle = !screenData.shuffle)))
                }
            }
            is CheckBoxAction.Omote -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(omote = !screenData.omote)))
                }
            }
            is CheckBoxAction.Ura -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(ura = !screenData.ura)))
                }
            }
        }
    }
    fun startSolving() {

    }
}
