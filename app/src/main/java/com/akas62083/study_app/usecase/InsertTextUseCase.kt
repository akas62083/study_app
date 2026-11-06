package com.akas62083.study_app.usecase

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.di.IoDispatcher
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.repository.data.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InsertTextUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(
        name: String,
        textType: TextType,
        folderId: Long,
    ) {
        val id = repository.insertText(
            TextEntity(
                name = name,
                textType = textType,
                folderId = folderId
            )
        )
        repository.updateText(
            TextEntity(
                id = id,
                sortId = id,
                name = name,
                folderId = folderId,
                textType = textType,
            )
        )
    }
}