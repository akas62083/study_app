package com.akas62083.study_app.repository.data

import com.akas62083.study_app.data.folder.FolderDao
import com.akas62083.study_app.data.folder.FolderEntity
import com.akas62083.study_app.data.folder_with_texts.FolderWithTexts
import com.akas62083.study_app.data.folder_with_texts.FolderWithTextsDao
import com.akas62083.study_app.data.text.TextDao
import com.akas62083.study_app.data.text.TextEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val textDao: TextDao,
    private val folderWithTextsDao: FolderWithTextsDao
): DataRepository {
    //---
    override suspend fun insertFolder(folder: FolderEntity): Long = folderDao.insertFolder(folder)
    override suspend fun deleteFolder(folder: FolderEntity) = folderDao.deleteFolder(folder)
    override suspend fun updateFolder(folder: FolderEntity) = folderDao.updateFolder(folder)
    override fun getAllFolders(): Flow<List<FolderEntity>> = folderDao.getAllFolders()
    override suspend fun getFolderById(id: Long): FolderEntity = folderDao.getFolderById(id)

    override suspend fun insertText(text: TextEntity): Long = textDao.insertText(text)
    override suspend fun deleteText(text: TextEntity) = textDao.deleteText(text)
    override suspend fun updateText(text: TextEntity) = textDao.updateText(text)
    override fun getAllTexts(): Flow<List<TextEntity>> = textDao.getAllTexts()
    override fun getTextById(id: Long) = textDao.getTextById(id)

    override fun getAllFolderWithTexts(): Flow<List<FolderWithTexts>> = folderWithTextsDao.getAllParentsWithChildren()
    //---
}