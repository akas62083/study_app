package com.akas62083.study_app.domain

// normal text
// vocabulary book
// fill-in-the-blank questions
enum class TextType {
    TEXT,
    BLANK,
    CARD
}

sealed interface TextStruct {
    data class Text(
        val text: String
    ): TextStruct
    data class Blank(
        val text: List<BlankTextStruct>
    ): TextStruct
    data class Card(
        val text: Set<CardTextStruct>,
        val tags: Set<String>,
        val headers: Set<String>
    ): TextStruct
    data class Error(
        val code: Int,
        val line: Int
    ): TextStruct
}

/*
0: デフォルトのコード。原因不明。
1: x行目のコロンの数が2じゃない。TextType=Card。MakeTextStructUseCase.kt
2: 'の数が偶数である。TextType=Blank。MakeTextStructUseCase.kt





*/

sealed interface BlankTextStruct {
    data class Text(
        val text: String = ""
    ): BlankTextStruct
    data class Blank(
        val text: String = "",
        val isOpened: Boolean = false
    ): BlankTextStruct
}

data class CardTextStruct(
    val question: String,
    val answer: String,
    val tags: Set<String>,
    val header: String,
)
