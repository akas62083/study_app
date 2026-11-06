package com.akas62083.study_app.usecase

import android.util.Log
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.BlankTextStruct
import com.akas62083.study_app.domain.CardTextStruct
import com.akas62083.study_app.domain.TextStruct
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.repository.data.DataRepository
import javax.inject.Inject

class MakeTextStructUseCase @Inject constructor(

) {
    operator fun invoke(textEntity: TextEntity): TextStruct {
        var result: TextStruct = TextStruct.Error(0, 0)
        when(textEntity.textType) {
            TextType.TEXT -> {
                result = TextStruct.Text(
                    text = textEntity.sentence
                )
            }
            TextType.CARD -> {
                val cards = mutableSetOf<CardTextStruct>()
                val headers = mutableSetOf<String>("")
                val tags = mutableSetOf<String>("")

                var header = ""
                var headerTags = setOf<String>("")

                val lineTexts = textEntity.sentence.split("\n")
                for(text in lineTexts) {
                    if(text == "") continue
                    if(text.count({it == ':'}) == 2) {
                        val cardText: List<String> = text.split(":").map {
                            it.replace(" ", "").replace("　", "")
                        }
                        tags += cardText[1].split(',').toSet() + ""
                        if(cardText[0] == "") {
                            header = cardText[2]
                            headers += header
                            headerTags = cardText[1].split(',').toSet() + ""
                            continue
                        }
                        cards.add(CardTextStruct(
                            question = cardText[0],
                            tags = cardText[1].split(',').toSet() + headerTags,
                            answer = cardText[2],
                            header = header
                        ))
                    } else {
                        result = TextStruct.Error(
                            code = 1,
                            line = lineTexts.indexOf(text) + 1
                        )
                        return result
                    }
                }
                if(cards.isNotEmpty()) {
                    result = TextStruct.Card(
                        text = cards,
                        tags = tags,
                        headers = headers
                    )
                } else {
                    result = TextStruct.Error(
                        code = 4,
                        line = 0
                    )
                }
            }
            TextType.BLANK -> {
                val blanks = mutableListOf<BlankTextStruct>()
                if(textEntity.sentence.count({it == '\''}) %2 == 1) {
                    result = TextStruct.Error(
                        code = 2,
                        line = 0
                    )
                    return result
                }
                if(textEntity.sentence == "") {
                    result = TextStruct.Blank(
                        text = listOf(BlankTextStruct.Text(""))
                    )
                    return result
                }
                var isBlank = textEntity.sentence[0] == '\''
                val texts = textEntity.sentence.split("'")
                for(text in texts) {
                    if(isBlank) {
                        blanks.add(BlankTextStruct.Blank(text, false))
                    } else {
                        blanks.add(BlankTextStruct.Text(text))
                    }
                    isBlank = !isBlank
                }
                result = TextStruct.Blank(
                    text = blanks
                )
            }
        }
        return result
    }
}
