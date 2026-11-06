package com.akas62083.study_app.usecase

import android.util.Log
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.text.TextEntity
import com.akas62083.study_app.repository.data.DataRepository
import javax.inject.Inject

class ChangeTextUseCase @Inject constructor(
    private val repository: DataRepository,
) {
    suspend operator fun invoke(textEntity: TextEntity) {
        Log.d("a", textEntity.sentence + " " + textEntity.id)
        repository.updateText(
            textEntity
        )
    }
}