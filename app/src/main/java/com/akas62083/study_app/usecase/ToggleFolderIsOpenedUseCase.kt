package com.akas62083.study_app.usecase

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.domain.TextType
import com.akas62083.study_app.repository.data.DataRepository
import javax.inject.Inject

class ToggleFolderIsOpenedUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(folder: FolderEntity) {
        repository.updateFolder(
            folder.copy(isOpened = !folder.isOpened)
        )
    }
}