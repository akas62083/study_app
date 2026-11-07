package com.akas62083.study_app.ui.detail

import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.CardTextStruct
import com.akas62083.study_app.domain.TextStruct

data class DetailUiState (
    val text: TextEntity? = null,
    val screen: DetailScreenState,
)

data class DetailScreenState(
    val tabStatus: TabStatus,
    val text: TextEntity? = null,
)

enum class TabType { // タブの識別のための enum . 関数の引数などに.
    SAVE,
    TEXT,
    EDIT,
    RUN
}
sealed interface TabStatus { // タブの情報を含む sealed interface . struct の texttype は信用しない情報とする。 texttype はここの中のものを使うこと。
    data object Save: TabStatus
    data class Text(
        val screenData: TextScreenData
    ): TabStatus
    data class Edit(
        val screenData: EditScreenData
    ): TabStatus
    data class Run(
        val screenData: RunScreenData
    ): TabStatus
    data object UnLoaded: TabStatus
}
sealed interface TextScreenData {
    data object Blank: TextScreenData
    data object Text: TextScreenData
    data object Card: TextScreenData
    data object Error: TextScreenData
}
sealed interface EditScreenData {
    data object Blank: EditScreenData
    data object Text: EditScreenData
    data object Card: EditScreenData
}
sealed interface RunScreenData {
    data object Blank: RunScreenData
    data object Text: RunScreenData
    data class Card(
        val status: CardStatus = CardStatus.FIRST,

        val tagsKinds: Set<String> = setOf(),
        val allTags: Boolean = false,
        val tags: Set<String> = setOf(),
        val headersKinds: Set<String> = setOf(),
        val allHeaders: Boolean = false,
        val headers: Set<String> = setOf(),
        val shuffle: Boolean = true,
        val omote: Boolean = true,
        val ura: Boolean = false,
        val unknown: Boolean = false,

        val problemSet: Set<CardTextStruct> = setOf(),
        val problemsList: List<CardTextStruct> = listOf(),
        val unknowns: List<CardTextStruct> = listOf()
    ): RunScreenData
    data class Error(
        val code: Int,
        val line: Int,
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
    data object Unknown: CheckBoxAction
}
