package com.akas62083.study_app.usecase

import coil.util.CoilUtils.result
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.di.IoDispatcher
import com.akas62083.study_app.repository.data.DataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTextByIdUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    operator fun invoke(id: Long): Flow<TextEntity> {
        return repository.getTextById(id)
    }
}
