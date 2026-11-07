package com.akas62083.study_app.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.study_app.Route
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.CardTextStruct
import com.akas62083.study_app.domain.TextStruct
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
    private val _screenState = MutableStateFlow(DetailScreenState(
        tabStatus = TabStatus.UnLoaded
    ))


    val uiState: StateFlow<DetailUiState> =
        combine(
            _screenState,
            getTextByIdUseCase(args.id)
        ) { screenState, textEntity ->
            DetailUiState(
                screen = screenState.copy(
                    text = screenState.text ?: textEntity,
                    tabStatus = if(screenState.tabStatus is TabStatus.UnLoaded) TabStatus.Edit(EditScreenData.Card) else screenState.tabStatus,
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState(
                screen = DetailScreenState(
                    tabStatus = TabStatus.UnLoaded
                )
            )
        )

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.TabClicked -> {
                saveText()
                tabClicked(event.tabType)
            }
            
            // Edit
            is DetailEvent.ChangeText -> { changeText(event.text) }

            // Run
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
        val textStruct = makeTextStructUseCase(uiState.value.screen.text ?: TextEntity(folderId = 0))
        _screenState.update { cs ->
            cs.copy(
                tabStatus = when(tabType) {
                    TabType.SAVE -> { TabStatus.Save }
                    TabType.TEXT -> { TabStatus.Text(
                        when(textStruct) {
                            is TextStruct.Text -> { TextScreenData.Text }
                            is TextStruct.Card -> { TextScreenData.Card }
                            is TextStruct.Blank -> { TextScreenData.Blank }
                            is TextStruct.Error -> { TextScreenData.Error }
                        }
                    ) }
                    TabType.EDIT -> { TabStatus.Edit(
                        when(uiState.value.text!!.textType) {
                            TextType.BLANK -> { EditScreenData.Blank }
                            TextType.CARD -> { EditScreenData.Card }
                            TextType.TEXT -> { EditScreenData.Text }
                        }
                    ) }
                    TabType.RUN -> { TabStatus.Run(
                        screenData = when(textStruct) {
                            is TextStruct.Text -> { RunScreenData.Text }
                            is TextStruct.Card -> { RunScreenData.Card(
                                tags = textStruct.tags,
                                headers = textStruct.headers,
                                problemSet = textStruct.text,
                                tagsKinds = textStruct.tags,
                                headersKinds = textStruct.headers,
                                status = CardStatus.FIRST
                            ) }
                            is TextStruct.Blank -> { RunScreenData.Blank }
                            is TextStruct.Error -> { RunScreenData.Error(
                                code = textStruct.code,
                                line = textStruct.line
                            ) }
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
            is CheckBoxAction.Unknown -> {
                _screenState.update { cs ->
                    cs.copy(tabStatus = tabStatus.copy(screenData = screenData.copy(unknown = !screenData.unknown)))
                }
            }

        }
    }
    fun startSolving() {
        val screenData = (uiState.value.screen.tabStatus as TabStatus.Run).screenData as RunScreenData.Card
        val result: MutableSet<CardTextStruct> = mutableSetOf()
        val selectedTags = if(screenData.allTags) screenData.tagsKinds else screenData.tags
        val selectedHeaders = if(screenData.allHeaders) screenData.headersKinds else screenData.headers

        screenData.problemSet.forEach { card ->
            if(card.tags.any { it in selectedTags } || card.header in selectedHeaders) {
                if(screenData.omote) {
                    result.add(CardTextStruct(
                        question = card.question,
                        answer = card.answer,
                        tags = card.tags,
                        header = card.header
                    ))
                }
                if(screenData.ura) {
                    result.add(CardTextStruct(
                        question = card.answer,
                        answer = card.question,
                        tags = card.tags,
                        header = card.header
                    ))
                }
            }
        }
        result += screenData.unknowns
        val resultList = result.toMutableList()
        if(screenData.shuffle) resultList.shuffle()

        _screenState.update { cs ->
            cs.copy(
                tabStatus = when(cs.tabStatus) {
                    is TabStatus.Run -> {
                        when(cs.tabStatus.screenData) {
                            is RunScreenData.Card -> {
                                cs.tabStatus.copy(
                                    screenData = when(cs.tabStatus.screenData) {
                                        is RunScreenData.Card -> { cs.tabStatus.screenData.copy(
                                            unknowns = emptyList(),
                                            problemsList = resultList,
                                            status = CardStatus.SOLVING,
                                        )}
                                        else -> { cs.tabStatus.screenData }
                                    }
                                )
                            }
                            else -> { cs.tabStatus }
                        }
                    }
                    else -> { cs.tabStatus }
                }
            )
        }

    }
}
