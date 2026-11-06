package com.akas62083.study_app.ui.move

import androidx.lifecycle.ViewModel
import com.akas62083.study_app.ui.detail.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoveViewModel @Inject constructor(

): ViewModel() {
    private val _uiState = MutableStateFlow(MoveUiState())
    val uiState: StateFlow<MoveUiState> = _uiState.asStateFlow()

    init {

    }
    fun onEvent(event: MoveEvent) {

    }
}