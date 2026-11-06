package com.akas62083.study_app.usecase

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.di.IoDispatcher
import com.akas62083.study_app.repository.data.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(folder: FolderEntity) {
        repository.deleteFolder(folder)
    }
}