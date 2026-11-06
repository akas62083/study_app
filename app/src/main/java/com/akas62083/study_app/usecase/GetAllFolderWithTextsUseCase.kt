package com.akas62083.study_app.usecase

import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.di.IoDispatcher
import com.akas62083.study_app.repository.data.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllFolderWithTextsUseCase @Inject constructor(
    private val repository: DataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<List<FolderWithTexts>> {
        val result = repository.getAllFolderWithTexts().flowOn(ioDispatcher)
        return result
    }
}