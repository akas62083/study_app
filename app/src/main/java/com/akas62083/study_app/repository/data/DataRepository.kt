package com.akas62083.study_app.repository.data

import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.data.text.TextEntity
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    //---
    suspend fun insertFolder(folder: FolderEntity): Long
    suspend fun deleteFolder(folder: FolderEntity)
    suspend fun updateFolder(folder: FolderEntity)
    fun getAllFolders(): Flow<List<FolderEntity>>
    suspend fun getFolderById(id: Long): FolderEntity

    suspend fun insertText(text: TextEntity): Long
    suspend fun deleteText(text: TextEntity)
    suspend fun updateText(text: TextEntity)
    fun getAllTexts(): Flow<List<TextEntity>>
    fun getTextById(id: Long): Flow<TextEntity>

    fun getAllFolderWithTexts(): Flow<List<FolderWithTexts>>
    //---
}