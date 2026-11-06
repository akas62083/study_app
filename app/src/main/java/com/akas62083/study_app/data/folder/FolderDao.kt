package com.akas62083.study_app.data.folder

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Insert
    suspend fun insertFolder(folder: FolderEntity): Long
    @Delete
    suspend fun deleteFolder(folder: FolderEntity)
    @Update
    suspend fun updateFolder(folder: FolderEntity)
    @Query("select * from folder_table order by folder_sort_id")
    fun getAllFolders(): Flow<List<FolderEntity>>
    @Query("select * from folder_table where folder_id = :id")
    suspend fun getFolderById(id: Long): FolderEntity
}