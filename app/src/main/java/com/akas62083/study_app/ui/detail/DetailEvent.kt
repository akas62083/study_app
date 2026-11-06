package com.akas62083.study_app.ui.detail

sealed interface DetailEvent {
    data class ChangeText(val text: String): DetailEvent
    data class TabClicked(val tabType: TabType): DetailEvent
    data class CheckBoxClicked(val checkBoxAction: CheckBoxAction): DetailEvent
    data object StartButtonClicked: DetailEvent
}