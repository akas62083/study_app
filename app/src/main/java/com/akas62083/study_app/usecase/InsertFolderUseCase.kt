package com.akas62083.study_app.usecase

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.di.IoDispatcher
import com.akas62083.study_app.repository.data.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertFolderUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(name: String, isRed: Boolean) {
        val id = repository.insertFolder(
            FolderEntity(
                name = name,
                isRed = isRed
            )
        )
        repository.updateFolder(
            FolderEntity(
                id = id,
                sortId = id,
                name = name,
                isRed = isRed
            )
        )
    }
}