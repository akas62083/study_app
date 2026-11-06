package com.akas62083.study_app.ui.detail

import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextStruct

data class DetailUiState (
    val text: TextEntity? = null,
    val screen: DetailScreenState = DetailScreenState()
)

data class DetailScreenState(
    val tabStatus: TabStatus = TabStatus.Edit,
    val text: TextEntity? = null,
    val textStruct: TextStruct = TextStruct.Error(3, 0)
)

enum class TabType { // タブの識別のための enum . 関数の引数などに.
    SAVE,
    TEXT,
    EDIT,
    RUN
}
sealed interface TabStatus { // タブの情報を含む sealed interface . struct の texttype は信用しない情報とする。 texttype はここの中のものを使うこと。
    data object Save: TabStatus
    data object Text: TabStatus
    data object Edit: TabStatus
    data class Run(
        val screenData: RunScreenData
    ): TabStatus
}

sealed interface RunScreenData {
    data object Blank: RunScreenData
    data object Text: RunScreenData
    data class Card(
        val status: CardStatus,

        val allTags: Boolean = false,
        val tags: Set<String> = setOf(),
        val allHeaders: Boolean = false,
        val headers: Set<String> = setOf(),
        val shuffle: Boolean = true,
        val omote: Boolean = true,
        val ura: Boolean = false
    ): RunScreenData
}

enum class CardStatus {
    FIRST,
    SOLVING,
    RESULT
}

sealed interface CheckBoxAction{
    data object AllTags: CheckBoxAction
    data class Tag(
        val tag: String
    ): CheckBoxAction
    data object AllHeaders: CheckBoxAction
    data class Header (
        val header: String
    ): CheckBoxAction
    data object Shuffle: CheckBoxAction
    data object Omote: CheckBoxAction
    data object Ura: CheckBoxAction
}